package com.npc.client.Minio.service.impl;

import com.npc.client.Minio.MinioUtil;
import com.npc.client.Minio.service.MinioService;
import com.npc.core.encrypt.base64.ImageUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: npcService
 * @description minio文件服务类实现
 * @author: feiyang
 * @create: 2025/11/30 17:35
 **/
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioUtil minioUtil;
    private MinioClient minioClient;

    @Override
    public String imageUpload(MultipartFile file, String type) throws IOException {
        byte[] bytes = ImageUtil.compressImage(file, "JPEG");
        return minioUtil.uploadFileByte(bytes, ".jpeg", "image/jpeg", type);
    }

    @Override
    public String fileUpload(MultipartFile file, String type) throws IOException {
        // 直接上传原始文件，不做图片压缩处理
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        return minioUtil.uploadFileWithType(file, extension, file.getContentType(), type);
    }

    @Override
    public List<String> listAllImages() throws Exception {
        // 获取存储桶中的所有对象
        List<String> allObjects = minioUtil.listObjects();

        // 过滤出图片文件（支持常见图片格式）
        return allObjects.stream()
                .filter(url -> url.toLowerCase().matches(".*\\.(jpg|jpeg|png|gif|bmp|webp|tiff)$"))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listAllFiles(String bucketName) throws Exception {
        // 复用 MinioUtil 中的方法，而不是直接使用 minioClient
        // 这里需要在 MinioUtil 中添加根据 bucketName 查询文件的方法
        return minioUtil.listObjects(bucketName); // 需要在 MinioUtil 中添加这个重载方法
    }
    @Override
    public List<String> listAllFiles(String bucketName, String prefix) throws Exception {
        return minioUtil.listObjectsByPrefix(bucketName, prefix);
    }

    @Override
    public List<String> listAllFilesInAllBuckets() throws Exception {
        List<String> allFiles = new ArrayList<>();

        // 获取所有存储桶名称（需要在 MinioUtil 中添加此方法）
        List<String> bucketNames = minioUtil.listBuckets();

        for (String bucketName : bucketNames) {
            // 复用 MinioUtil 中的方法查询每个存储桶的文件
            allFiles.addAll(minioUtil.listObjects(bucketName)); // 需要 MinioUtil 支持指定 bucketName
        }

        return allFiles;
    }

    @Override
    public void imageDownload(HttpServletResponse response, String url)throws IOException {
        minioUtil.downloadFile(response, url);
    }

    @Override
    public void videoStream(HttpServletResponse response, String url) throws IOException {
        minioUtil.streamFile(response, url);
    }


    @Override
    public void imageDelete(String url) throws Exception {
        if (!minioUtil.fileExists(url)) {
            throw new Exception("文件不存在");
//            throw new FileException("文件不存在");
        }
        minioUtil.deleteFile(url);
    }
}
