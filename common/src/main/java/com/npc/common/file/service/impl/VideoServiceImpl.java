package com.npc.common.file.service.impl;

import com.alipay.api.internal.util.file.IOUtils;
import com.npc.common.file.entity.Video;
import com.npc.common.file.mapper.VideoMapper;
import com.npc.common.file.service.IVideoService;
import com.npc.common.modular.file.entity.Files;
import com.npc.common.modular.file.mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-04-08
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);
    @Resource
    private FileMapper fileMapper;
    /**
     * 生成m3u8文件，用于是视频流播放
     */
    @Override
    public Boolean generateM3U8(Integer videoId) {
        String fileDir = "E:\\Data\\";
        String tepDir = "E:\\Data\\m3u8tmp\\";
        Files files = fileMapper.selectById(videoId);
        String inputVideo = fileDir + files.getPath();
        String outputPlaylist = tepDir + videoId + "\\" + videoId + "-.m3u8";
        // ffmpeg command and arguments
        String[] command = {
                "ffmpeg",
                "-i", inputVideo,
                "-c:v", "libx264",
                "-c:a", "aac",
                "-f", "hls",
                "-hls_time", "10",
                "-hls_list_size", "0",
                outputPlaylist
        };
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        File folder = new File(tepDir + videoId);
        if (!folder.exists()) {
            boolean created = folder.mkdirs(); // 创建多级目录
        }
        // 设置工作目录为项目所在的目录
        processBuilder.directory(folder);
        Process process = null;
        try {
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            String result = IOUtils.toString(process.getInputStream()); // 一次性获取运行结果
            int exitCode = process.waitFor(); // 等到运行结束
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }
}
