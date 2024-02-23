package com.npc.common.modular.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.npc.common.file.utils.FileTypes;
import com.npc.common.modular.file.entity.Files;
import com.npc.common.modular.file.mapper.FileMapper;
import com.npc.common.modular.file.service.IFileService;
import com.npc.core.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 缓存文件 服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-08-05
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Resource
    private FileMapper fileMapper;

    /**
     * 加载文件到file表中，实现缓存
     */
    @Override
    public Boolean cacheFiles(Integer id) {
        String basicDir = "E:\\Data";
        List<Files> fileList = new ArrayList<>();

        // 获取本地文件目录
        File directory = new File(basicDir);
        if (id != 0) {
            Files files = fileMapper.selectById(id);
            directory = new File(basicDir + files.getPath());
        }
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_dir_id", id);
        List<Files> list = list(queryWrapper);
        if (list.size() > 0 ) return true;
        // 遍历目录下的所有文件，将文件信息保存到 fileList 中
        for (File file : directory.listFiles()) {
            Files files = new Files();
            String fileName = file.getName();
            if (file.isDirectory()) {
                files.setType("folder");
            } else {
                files.setType(FileTypes.getFileType(fileName));
            }
//            系统文件缩略图
//            Icon icon = FileUtils.GetSmallIcon(file);
////            Icon icon = FileUtils.GetBigIcon(file);
//            Image image = ((ImageIcon) icon).getImage(); // 转换为 Image 对象
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            try {
//                ImageIO.write((RenderedImage) image, "png", baos);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            files.setParentDirId(id);
            files.setName(fileName);
            long size = FileUtils.getFolderSize(file);
            files.setSize((double) size);
            files.setMd5("");
            files.setCreTime(Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime());

            files.setPath(file.getAbsolutePath().replace(basicDir, ""));
            fileList.add(files);
        }
        return saveBatch(fileList);
    }
}
