package com.npc.client.Minio;

import cn.hutool.core.lang.UUID;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: npcService
 * @description minio工具类
 * 文件操作工具类
 * 生产环境需配置 MinIO 集群确保高可用；敏感文件需通过预签名 URL 控制访问权限；定期备份桶数据以防丢失
 * @author: feiyang
 * @create: 2025/11/28 21:38
 **/
@RequiredArgsConstructor
@Component
@Slf4j
public class MinioUtil {

    private final MinioProperties minioProperties;
    private MinioClient minioClient;
    private String bucketName;

    private LocalDate retainSince = LocalDate.of(2025, 6, 1);

    private static final String SERVICE_URL = "https://minio.mmsx.xyz";


    // 初始化 Minio 客户端
    @PostConstruct
    public void init() {
        try {
            //创建客户端
            minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getUrl())
                    .credentials(minioProperties.getUsername(), minioProperties.getPassword())
                    .build();
            bucketName = minioProperties.getBucketName();

            // 检查桶是否存在，不存在则创建
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            throw new RuntimeException("Minio 初始化失败", e);
        }
    }

    /**
     * 获取所有存储桶名称
     *
     * @return 存储桶名称列表
     * @throws Exception 操作异常
     */
    public List<String> listBuckets() throws Exception {
        List<String> bucketNames = new ArrayList<>();
        List<Bucket> buckets = minioClient.listBuckets();
        for (Bucket bucketResult : buckets) {
            bucketNames.add(bucketResult.name());
        }
        return bucketNames;
    }

    /**
     * 上传文件（保持原始格式）
     *
     * @param file        原始文件
     * @param extension   文件扩展名
     * @param contentType 文件 MIME 类型
     * @param type        文件类型分类
     * @return MinIO 中的文件路径（格式：/bucketName/type/yyyy-MM-dd/uuid.extension）
     */
    public String uploadFileWithType(MultipartFile file, String extension, String contentType, String type) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }

        try {
            // 生成带类型和日期的文件名
            String uniqueFilename = generateUniqueFilenameWithType(extension, type);

            // 上传到 MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(uniqueFilename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(contentType)
                            .build()
            );

            return SERVICE_URL + "/" + bucketName + "/" + uniqueFilename;
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }


    /*
     * 上传文件
     */
    public String uploadFile(MultipartFile file, String extension) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        }

        try {
            // 生成唯一文件名
            String uniqueFilename = generateUniqueFilename(extension);

            // 上传文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(uniqueFilename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            return "/" + bucketName + "/" + uniqueFilename;
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }


    /**
     * 上传已处理的图片字节数组到 MinIO
     *
     * @param imageData   处理后的图片字节数组
     * @param extension   文件扩展名（如 ".jpg", ".png"）
     * @param contentType 文件 MIME 类型（如 "image/jpeg", "image/png"）
     * @return MinIO 中的文件路径（格式：/bucketName/yyyy-MM-dd/uuid.extension）
     */
    public String uploadFileByte(byte[] imageData, String extension, String contentType) {
        if (imageData == null || imageData.length == 0) {
            throw new RuntimeException("上传的图片数据不能为空");
        }
        if (extension == null || extension.isEmpty()) {
            throw new IllegalArgumentException("文件扩展名不能为空");
        }
        if (contentType == null || contentType.isEmpty()) {
            throw new IllegalArgumentException("文件 MIME 类型不能为空");
        }

        try {
            // 生成唯一文件名
            String uniqueFilename = generateUniqueFilename(extension);

            // 上传到 MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(uniqueFilename)
                            .stream(new ByteArrayInputStream(imageData), imageData.length, -1)
                            .contentType(contentType)
                            .build()
            );

            return SERVICE_URL + "/" + bucketName + "/" + uniqueFilename;
        } catch (Exception e) {
            throw new RuntimeException("处理后的图片上传失败", e);
        }
    }
    /**
     * 上传已处理的图片字节数组到 MinIO
     *
     * @param imageData   处理后的图片字节数组
     * @param extension   文件扩展名（如 ".jpg", ".png"）
     * @param contentType 文件 MIME 类型（如 "image/jpeg", "image/png"）
     * @param type        文件类型分类
     * @return MinIO 中的文件路径（格式：/bucketName/type/yyyy-MM-dd/uuid.extension）
     */
    public String uploadFileByte(byte[] imageData, String extension, String contentType, String type) {
        if (imageData == null || imageData.length == 0) {
            throw new RuntimeException("上传的图片数据不能为空");
        }
        if (extension == null || extension.isEmpty()) {
            throw new IllegalArgumentException("文件扩展名不能为空");
        }
        if (contentType == null || contentType.isEmpty()) {
            throw new IllegalArgumentException("文件 MIME 类型不能为空");
        }

        try {
            // 生成带类型和日期的文件名
            String uniqueFilename = generateUniqueFilenameWithType(extension, type);

            // 上传到 MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(uniqueFilename)
                            .stream(new ByteArrayInputStream(imageData), imageData.length, -1)
                            .contentType(contentType)
                            .build()
            );

            return SERVICE_URL + "/" + bucketName + "/" + uniqueFilename;
        } catch (Exception e) {
            throw new RuntimeException("处理后的图片上传失败", e);
        }
    }

    /**
     * 生成带类型分类的唯一文件名（type/yyyy-MM-dd/uuid.extension）
     */
    private String generateUniqueFilenameWithType(String extension, String type) {
        String dateFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uuid = UUID.randomUUID().toString().replace("-", ""); // 去掉 UUID 中的 "-"
        return type + "/" + dateFormat + "/" + uuid + extension;
    }

    /**
     * 上传本地生成的 Excel 临时文件到 MinIO
     *
     * @param localFile 本地临时文件路径
     * @param extension 扩展名
     * @return MinIO 存储路径，格式：/bucketName/yyyy-MM-dd/targetName
     */
    public String uploadLocalExcel(Path localFile, String extension) {
        if (localFile == null || !Files.exists(localFile)) {
            throw new RuntimeException("本地文件不存在");
        }
        try (InputStream in = Files.newInputStream(localFile)) {
            String objectKey = generateUniqueFilename(extension); // 保留日期目录
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectKey)
                            .stream(in, Files.size(localFile), -1)
                            .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                            .build());
            return "/" + bucketName + "/" + objectKey;
        } catch (Exception e) {
            throw new RuntimeException("Excel 上传失败", e);
        }
    }

    /*
     * 根据URL下载文件
     */
    public void downloadFile(HttpServletResponse response, String fileUrl) {
        if (fileUrl == null || !fileUrl.contains(bucketName + "/")) {
            throw new IllegalArgumentException("无效的文件URL");
        }

        try {
            // 从URL中提取对象路径和文件名
            String objectUrl = fileUrl.split(bucketName + "/")[1];
            String fileName = objectUrl.substring(objectUrl.lastIndexOf("/") + 1);

            // 设置响应头
            response.setContentType("application/octet-stream");
            String encodedFileName = URLEncoder.encode(fileName, String.valueOf(StandardCharsets.UTF_8)).replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");

            // 下载文件
            try (InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectUrl)
                    .build());
                 OutputStream outputStream = response.getOutputStream()) {

                // 用IOUtils.copy高效拷贝（内部缓冲区默认8KB）
                IOUtils.copy(inputStream, outputStream);
            }
        } catch (Exception e) {
            throw new RuntimeException("文件下载失败", e);
        }
    }

    /**
     * 列出存储桶中的所有对象
     *
     * @return 对象URL列表
     * @throws Exception 操作异常
     */
    public List<String> listObjects() throws Exception {
        List<String> objectUrls = new ArrayList<>();

        Iterable<Result<Item>> results = minioClient.listObjects(
            ListObjectsArgs.builder()
                .bucket(bucketName)
                .build()
        );

        for (Result<Item> result : results) {
            Item item = result.get();
            // 构造完整的URL或使用对象名
            String url = getObjectUrl(item.objectName());
            objectUrls.add(url);
        }

        return objectUrls;
    }

    /**
     * 列出指定存储桶中的所有对象
     *
     * @param bucketName 存储桶名称
     * @return 对象URL列表
     * @throws Exception 操作异常
     */
    public List<String> listObjects(String bucketName) throws Exception {
        List<String> objectUrls = new ArrayList<>();

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );

        for (Result<Item> result : results) {
            Item item = result.get();
            String url = SERVICE_URL + "/" + bucketName + "/" + item.objectName();
            objectUrls.add(url);
        }

        return objectUrls;
    }

    /**
     * 列出指定存储桶中指定前缀下的所有对象
     *
     * @param bucketName 存储桶名称
     * @param prefix     对象前缀（目录路径），可为null
     * @return 对象URL列表
     * @throws Exception 操作异常
     */
//    public List<String> listObjectsByPrefix(String bucketName, String prefix) throws Exception {
//        List<String> objectUrls = new ArrayList<>();
//
//        ListObjectsArgs.Builder builder = ListObjectsArgs.builder()
//                .bucket(bucketName);
//
//        if (prefix != null && !prefix.isEmpty()) {
//            builder.prefix(prefix);
//        }
//
//        Iterable<Result<Item>> results = minioClient.listObjects(builder.build());
//
//        for (Result<Item> result : results) {
//            Item item = result.get();
//            String url = SERVICE_URL + "/" + bucketName + "/" + item.objectName();
//            objectUrls.add(url);
//        }
//
//        return objectUrls;
//    }
    public List<String> listObjectsByPrefix(String bucketName, String prefix) throws Exception {
        List<String> fileList = new ArrayList<>();
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(prefix)
                        .recursive(false) // 设置为false，只列出当前目录下的文件和子目录
                        .build()
        );

//        for (Result<Item> result : results) {
//            Item item = result.get();
//            // 构造完整的URL路径
//            String fullPath = minioClient.getPresignedObjectUrl(
//                    GetPresignedObjectUrlArgs.builder()
//                            .method(Method.GET)
//                            .bucket(bucketName)
//                            .object(item.objectName())
//                            .expiry(24, TimeUnit.HOURS)
//                            .build()
//            );
//            fileList.add(fullPath);
//        }
        for (Result<Item> result : results) {
            Item item = result.get();
            String url = SERVICE_URL + "/" + bucketName + "/" + item.objectName();
            fileList.add(url);
        }
        return fileList;
    }

    /**
     * 根据对象名称生成完整的对象URL
     *
     * @param objectName 对象名称
     * @return 完整的对象URL
     */
    public String getObjectUrl(String objectName) {
        return SERVICE_URL + "/" + bucketName + "/" + objectName;
    }

    /**
     * 根据 MinIO 路径生成带签名的直链
     *
     * @param objectUrl 已存在的 MinIO 路径（/bucketName/...）
     * @param minutes   链接有效期（分钟）
     * @return 可直接访问的 HTTPS 下载地址
     */
    public String parseGetUrl(String objectUrl, int minutes) {
        if (objectUrl == null || !objectUrl.startsWith("/" + bucketName + "/")) {
            throw new IllegalArgumentException("非法的 objectUrl");
        }
        String objectKey = objectUrl.substring(("/" + bucketName + "/").length());
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectKey)
                            .expiry(minutes, TimeUnit.MINUTES)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("生成直链失败", e);
        }
    }

    /*
     * 根据URL删除文件
     */
    public void deleteFile(String fileUrl) {
        try {
            // 从URL中提取对象路径
            String objectUrl = fileUrl.split(bucketName + "/")[1];
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectUrl)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("文件删除失败", e);
        }
    }

    /*
     * 检查文件是否存在
     */
    public boolean fileExists(String fileUrl) {
        if (fileUrl == null || !fileUrl.contains(bucketName + "/")) {
            return false;
        }

        try {
            String objectUrl = fileUrl.split(bucketName + "/")[1];
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectUrl)
                    .build());
            return true;
        } catch (Exception e) {
            if (e instanceof ErrorResponseException && ((ErrorResponseException) e).errorResponse().code().equals("NoSuchKey")) {
                return false;
            }
            throw new RuntimeException("检查文件存在失败", e);
        }
    }


    /**
     * 生成唯一文件名（带日期路径 + UUID）
     */
    private String generateUniqueFilename(String extension) {
        String dateFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String uuid = UUID.randomUUID().toString().replace("-", ""); // 去掉 UUID 中的 "-"
        return dateFormat + "/" + uuid + extension;
    }

    /**
     * 删除早于指定日期的所有日期目录（yyyy-MM-dd/）
     *
     * @param endExclusive 截止日期（不含）
     * @return 实际删除的对象总数
     */
    public int deleteDateFoldersBefore(LocalDate endExclusive) {
        if (endExclusive == null) {
            throw new IllegalArgumentException("指定日期不能为空");
        }

        LocalDate today = LocalDate.now();
        if (!endExclusive.isBefore(today)) {
            return 0;
        }
        int totalDeleted = 0;
        // 从 endExclusive-1 天开始往前删
        for (LocalDate d = endExclusive.minusDays(1); !d.isBefore(retainSince); d = d.minusDays(1)) {
            totalDeleted += deleteSingleFolder(d.format(DateTimeFormatter.ISO_LOCAL_DATE) + "/");
        }
        return totalDeleted;
    }

    /**
     * 流式传输文件（适用于视频播放）
     *
     * @param response HTTP响应对象
     * @param fileUrl  文件URL
     */
    public void streamFile(HttpServletResponse response, String fileUrl) {
        if (fileUrl == null || !fileUrl.contains(bucketName + "/")) {
            throw new IllegalArgumentException("无效的文件URL");
        }

        try {
            // 从URL中提取对象路径和文件名
            String objectUrl = fileUrl.split(bucketName + "/")[1];
            String fileName = objectUrl.substring(objectUrl.lastIndexOf("/") + 1);

            // 设置响应头以支持流式传输
            response.setContentType("video/mp4"); // 默认设置为mp4格式
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

            // 流式传输文件
            try (InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectUrl)
                    .build());
                 OutputStream outputStream = response.getOutputStream()) {

                // 使用缓冲区提高传输效率
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException("文件流传输失败", e);
        }
    }

    /**
     * 删除单个目录（前缀）下的全部对象
     * 暂时只能删除 temp 临时目录
     */
    private int deleteSingleFolder(String prefix) {
        try {
            List<DeleteObject> objects = new ArrayList<>();
            minioClient.listObjects(ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix("/temp/" + prefix)
                            .recursive(true)
                            .build())
                    .forEach(r -> {
                        try {
                            objects.add(new DeleteObject(r.get().objectName()));
                        } catch (Exception ignored) {
                            log.warn("文件名获取失败");
                        }
                    });
            if (objects.isEmpty()) {
                return 0;
            }
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                    RemoveObjectsArgs.builder()
                            .bucket(bucketName)
                            .objects(objects)
                            .build());


            for (Result<DeleteError> res : results) {
                DeleteError deleteError = res.get();// 无异常即成功
            }
            return objects.size();
        } catch (Exception e) {
            log.warn("删除目录 {} 失败: {}", prefix, e.toString());
            return 0;
        }
    }
}