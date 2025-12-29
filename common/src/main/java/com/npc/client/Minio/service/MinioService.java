package com.npc.client.Minio.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program: npcService
 * @description minio文件服务类
 * @author: feiyang
 * @create: 2025/11/30 17:32
 **/
public interface MinioService {
    String imageUpload(MultipartFile file, String type) throws IOException;

    /**
     * 通用文件上传方法
     * @param file 要上传的文件
     * @param type 文件类型分类
     * @return 文件访问URL
     */
    String fileUpload(MultipartFile file, String type) throws IOException;

    /**
     * 查询所有图片
     *
     * @return 图片链接列表
     * @throws Exception 操作异常
     */
    List<String> listAllImages() throws Exception;

    /**
     * 列出存储桶中的所有对象
     * 查询指定存储桶文件的方法
     * @return 存储桶中的所有对象URL列表
     * @throws Exception 操作异常
     */
    List<String> listAllFiles(String bucketName) throws Exception;
    // 添加新的方法重载
    List<String> listAllFiles(String bucketName, String prefix) throws Exception;

    // 添加查询所有存储桶文件的方法
    List<String> listAllFilesInAllBuckets() throws Exception;


    void imageDownload(HttpServletResponse response, String url) throws IOException;

    /**
     * 视频流式传输方法
     * @param response HTTP响应对象
     * @param url 视频文件URL
     * @throws IOException IO异常
     */
    void videoStream(HttpServletResponse response, String url) throws IOException;

    void imageDelete(String url) throws Exception;

}