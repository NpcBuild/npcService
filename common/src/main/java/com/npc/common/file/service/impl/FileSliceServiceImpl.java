package com.npc.common.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.npc.common.file.entity.FileSlice;
import com.npc.common.file.mapper.FileSliceMapper;
import com.npc.common.file.service.FileSliceService;
import com.npc.core.utils.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
//import sun.awt.shell.ShellFolder;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.Comparator;
import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2023/3/5 17:53
 */
@Service
public class FileSliceServiceImpl implements FileSliceService {

    private static final String UPLOAD_DIR = "E:\\Data\\Upload\\";

    @Autowired
    private FileSliceMapper fileSliceMapper;

    /**
     * file 分片文件
     * fileInfo 分片文件信息
     */
    @Override
    public long saveChunk(MultipartFile file, FileSlice fileInfo) throws IOException {
        if (file != null && !file.isEmpty() && !StringUtils.isEmpty(fileInfo.getName())) {
            // 检查是否已存在该文件块
            int count = fileSliceMapper.selectChunkCount(fileInfo);
            if (count == 0) {
                // 不存在则保存文件块
                String chunkFolder = UPLOAD_DIR + fileInfo.getMd5();
                File folder = new File(chunkFolder);
                if (!folder.exists()) {
                    boolean created = folder.mkdirs(); // 创建多级目录
                    if (!created) {
//                        throw new IOException("无法创建目录：" + chunkFolder);
                    }
                }

                String tempFileName = fileInfo.getName() + "_part_" + fileInfo.getChunkIndex();
                File tempFile = new File(chunkFolder, tempFileName);
                if (!tempFile.exists()) {
                    boolean created = tempFile.createNewFile();
                    if (!created) {
                        throw new IOException("无法创建文件：" + tempFile.getAbsolutePath());
                    }
                }


                try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
                    byte[] bytes = file.getBytes();
                    stream.write(bytes);
                    stream.flush(); // 刷新缓冲区，确保数据写入磁盘
                }
                fileSliceMapper.insert(fileInfo);
            }
            if (fileInfo.getTotalChunks().longValue() == getUploadedChunks(fileInfo.getMd5())) {
                // 所有分片已上传，进行文件合并操作
                mergeChunks(fileInfo);
                System.out.println("File upload complete");
            }
            System.out.println("Chunk uploaded successfully");
            return count;
        }
        return -1;
    }

    @Override
    public void mergeChunks(FileSlice fileInfo) throws IOException {
        // 根据文件md5获取已上传的文件块列表
        List<FileSlice> chunkList = fileSliceMapper.selectChunksByMd5(fileInfo.getMd5());
        // 按照文件块索引排序
        chunkList.sort(Comparator.comparingInt(FileSlice::getChunkIndex));

        // 创建目标文件
        File destFile = new File(UPLOAD_DIR, fileInfo.getName());
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        // 合并文件块
        try (FileOutputStream outputStream = new FileOutputStream(destFile)) {
            for (FileSlice chunk : chunkList) {
                FileInputStream inputStream = new FileInputStream(UPLOAD_DIR + fileInfo.getMd5() + "\\" +  fileInfo.getName() + "_part_" + chunk.getChunkIndex());
                IOUtils.copy(inputStream, outputStream);
                inputStream.close();
            }
        }

        // 删除临时文件
        for (FileSlice chunk : chunkList) {
            File file = new File(UPLOAD_DIR + fileInfo.getMd5() + "\\" + fileInfo.getName() + "_part_" + chunk.getChunkIndex());
            file.delete();
        }
        // 删除临时文件夹
        File file = new File(UPLOAD_DIR + fileInfo.getMd5());
        file.delete();

//        // 保存文件信息到数据库
//        fileInfo.setSize(destFile.length());
////        fileInfo.setCreateTime(new Date());
//        fileSliceMapper.update(fileInfo);
    }

    @Override
    public long getUploadedChunks(String fileMd5) {
        return fileSliceMapper.selectUploadedChunks(fileMd5);
    }

    @Override
    public long getTotalChunks(String fileMd5) {
        QueryWrapper<FileSlice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",fileMd5);
//        FileSlice fileSlice = fileSliceService.getOne(queryWrapper);
//        long fileSize = fileSlice.getSize();
        long fileSize = 100000;
//        int chunkSize = fileInfo.getChunkSize();
        int chunkSize = 200000;
        int totalChunks = (int) Math.ceil((double) fileSize / chunkSize);
        return totalChunks;
    }
}
