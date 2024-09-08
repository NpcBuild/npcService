package com.npc.common.picBed;

import com.npc.core.ServerResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author NPC
 * @description
 * @create 2024/5/9 9:29
 */
@RestController
@RequestMapping("/images")
public class FileController {
    @Autowired
    private ImagesService imagesService;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public ServerResponseVO<?> upload(MultipartFile file) throws IOException {
        String url = imagesService.imageUploadGetUrl(file);
        return ServerResponseVO.success(url);
    }

    /**
     * 获取文件列表
     * @param dto
     * @return
     * @throws IOException
     */
    @PostMapping("/getImagesList")
    public ServerResponseVO<?> getImagesList(Map dto) throws IOException {
//        String url = imagesService.getImagesList(dto);
        return ServerResponseVO.success();
    }

    @DeleteMapping("/removeImages/{id}")
    public ServerResponseVO<?> removeImages(@PathVariable("id") String id) {
        imagesService.removeCloudImage(id);
        return ServerResponseVO.success("删除成功");
    }
}
