package com.npc.client.Minio.controller;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import com.npc.client.Minio.service.MinioService;
import com.npc.core.ServerResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: npcService
 * @description minio 控制器类
 * 后续扩展： 基于此框架扩展功能，添加文件权限控制（通过 MinIO 的 Policy）、文件分片上传（大文件处理）、定期清理过期文件等。
 * @author: feiyang
 * @create: 2025/11/30 17:30
 **/

//@Api(tags = "文件")
@RestController
@RequestMapping("/minio")
@RequiredArgsConstructor
public class MinioController {

    private final MinioService minioService;

    //    @ApiOperation("图片上传")
    @PostMapping("/image")
    public ServerResponseVO<?> imageUpload(MultipartFile file,
                                           @RequestParam(required = false, defaultValue = "default") String type) throws IOException {
        String url = minioService.imageUpload(file, type);
        return ServerResponseVO.success(url);
    }

    //    @ApiOperation("文件上传")
    @PostMapping("/file")
    public ServerResponseVO<?> fileUpload(MultipartFile file,
                                          @RequestParam(required = false, defaultValue = "default") String type) throws IOException {
        String url = minioService.fileUpload(file, type);
        return ServerResponseVO.success(url);
    }

//    @ApiOperation("全部图片")
    @GetMapping("/allImage")
    public ServerResponseVO<?> imageDownLoad()throws Exception {
        return ServerResponseVO.success(minioService.listAllImages());
    }

    @GetMapping("/files")
    public ServerResponseVO<?> listAllFiles(@RequestParam(required = false) String bucketName,
                                            @RequestParam(required = false) String prefix) throws Exception {
        if (bucketName != null && !bucketName.isEmpty()) {
            // 如果提供了prefix参数，查询该目录下的所有文件
            if (prefix != null && !prefix.isEmpty()) {
                // 确保前缀以"/"结尾来表示一个目录
                if (!prefix.endsWith("/")) {
                    prefix = prefix + "/";
                }
                return ServerResponseVO.success(minioService.listAllFiles(bucketName, prefix));
            } else {
                // 查询存储桶根目录下的所有文件
                return ServerResponseVO.success(minioService.listAllFiles(bucketName, ""));
            }
        } else {
            // 查询所有存储桶（保持原逻辑）
            return ServerResponseVO.success(minioService.listAllFilesInAllBuckets());
        }
    }

    //    @ApiOperation("图片下载")
    @GetMapping("/image")
    public void imageDownLoad(HttpServletResponse response, String url)throws IOException {
        minioService.imageDownload(response, url);
    }

    @GetMapping("/video/play")
    public void videoPlay(HttpServletResponse response, String url) throws IOException {
        minioService.videoStream(response, url);
    }

//    @ApiOperation("图片删除")
    @DeleteMapping("/image")
    public ServerResponseVO<?> imageDelete(String url) throws Exception {
        minioService.imageDelete(url);
        return ServerResponseVO.success();
    }

}
