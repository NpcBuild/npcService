package com.npc.common.netDisk.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.npc.common.file.entity.FileInfor;
import com.npc.common.file.entity.FileSlice;
import com.npc.common.file.param.FileParam;
import com.npc.common.file.service.FileSliceService;
import com.npc.common.file.utils.FileNormalOperation;
import com.npc.common.file.utils.FileTypes;
import com.npc.common.modular.file.entity.Files;
import com.npc.common.modular.file.service.IFileService;
import com.npc.common.modular.file.vo.FileVO;
import com.npc.core.ServerResponseVO;
import com.npc.core.limit.aop.Limit;
import com.npc.core.utils.FileUtils;
import com.npc.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @author NPC
 * @description 在线网盘
 * @create 2023/3/5 17:12
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/netDisk")
public class NetDiskController {

    @Value("${yf.basic-dir}")
    private String basicDir;
    @Autowired
    private FileSliceService fileSliceService;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private IFileService fileService;

    @Limit(key = "Limiter:netDisk",permitsPerSecond=1,timeout=500)
    @GetMapping("/test")
    public ServerResponseVO test() {
        String res = "成功！！！！";
        return ServerResponseVO.success(res);
    }
    @PostMapping("/filelist")
    public ServerResponseVO filelist(@RequestBody FileVO param) {
        fileService.cacheFiles(param.getId());
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_dir_id", param.getId());
        List<Files> list = fileService.list(queryWrapper);
        Map data = new HashMap();
        data.put("data", list);
        return ServerResponseVO.success(data);
    }

    /**
     * 服务器分片校验
     * @return
     */
    @GetMapping("/upload")
    public ServerResponseVO uploadCheck(FileParam param) {
        Map map = new HashMap();
        map.put("skipUpload", false); //秒传
        map.put("uploaded",new ArrayList<>()); //断点续传
        return ServerResponseVO.success(map);
    }

    /**
     * 分片上传接口
     * @return
     */
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ServerResponseVO upload(FileParam param) {
        FileSlice fileInfo = new FileSlice();
        fileInfo.setName(param.getFilename());
        fileInfo.setMd5(param.getIdentifier());
        fileInfo.setSize((long) param.getCurrentChunkSize());
        fileInfo.setChunkIndex(param.getChunkNumber());
        fileInfo.setTotalChunks(param.getTotalChunks());
        // 保存分片到临时目录
        try {
            fileSliceService.saveChunk(param.getFile(), fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // 返回上传进度
        long uploadedChunks = fileSliceService.getUploadedChunks(param.getIdentifier());
        long totalChunks = fileSliceService.getTotalChunks(param.getIdentifier());
        double progress = (double) uploadedChunks / totalChunks;
        return ServerResponseVO.success(progress);
    }

    /**
     * 下载文件
     * @return
     * @throws IOException
     */
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile() throws IOException {
        Resource file = resourceLoader.getResource("classpath:logo.png");
        InputStreamResource resource = new InputStreamResource(file.getInputStream());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=logo.png");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @PostMapping("/getusesize")
    public ServerResponseVO getusesize() {
        Map data = new HashMap();
        data.put("realsize", 52428800);
        data.put("size", 104857600);
        return ServerResponseVO.success(data);
    }

    @PostMapping("/getFile")
    public ServerResponseVO getFile(@RequestBody String yFilePath) {
        String data = FileUtils.thumbnail(basicDir + yFilePath, 1000, 1000);
        return ServerResponseVO.success(data);
    }

    /**
     * @description: MP4文件在线播放
     * // fixme 跨域问题没解决
     * @author: Re、ZOO2
     * @date: 2021/7/25 22:55
     * @param: [request, response, floderPath文件夹路径, fileName文件名称]
     * @return: com.lvmvp.configconsts.constant.ResultView
     **/
    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping(value = "/playMp4/{fileName}",produces ="application/json;charset=utf-8")
    public ServerResponseVO playMp4(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable("fileName") String fileName){
        String floderPath = "E:\\Data\\Computer\\Wallpaper\\4K";
        FileNormalOperation.aloneVideoPlay(request,response,floderPath,fileName);
        return null;
    }

    /**
     * 获取图片缩略图
     * @param id
     * @return
     */
    @GetMapping("/getImgThumbnail")
    public ServerResponseVO getImgThumbnail(String id) {
        Files file = fileService.getById(id);
        if (file.getIcon() != null) {
            return ServerResponseVO.success(file.getIcon());
        }
        String base64Image = "";
//            String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
        if ("image".equals(file.getType())) {
            base64Image = FileUtils.thumbnail(basicDir + file.getPath());
        }
        try {
            Files files = new Files();
            files.setIcon(base64Image.getBytes());
            UpdateWrapper<Files> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            fileService.update(files,updateWrapper);
        } catch (Exception e) {
            System.out.println("更新失败！" + e);
        }
        return ServerResponseVO.success(base64Image);
    }

}
