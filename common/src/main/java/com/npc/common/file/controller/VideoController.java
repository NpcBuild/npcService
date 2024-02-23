package com.npc.common.file.controller;

import com.npc.common.file.mapper.VideoMapper;
import com.npc.common.file.service.IVideoService;
import com.npc.common.modular.file.entity.Files;
import com.npc.common.modular.file.mapper.FileMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;

/**
 * @author NPC
 * @description
 * @create 2023/4/8 20:55
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/video")
public class VideoController {
    private final String BASE_VIDEO_PATH = "E:\\Data\\Upload\\";
    @Resource
    private VideoMapper videoMapper;
    @Resource
    private IVideoService videoService;
    @Resource
    private FileMapper fileMapper;

//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
////    @GetMapping(value = "/{id}", produces = "application/vnd.apple.mpegurl")
//    public ResponseEntity<StreamingResponseBody> getVideo(@PathVariable Long id) {
//        // simulate generating an M3U8 playlist with two video segments
//        List<String> segments = Arrays.asList("segment1.ts", "segment2.ts");
//        String playlist = "#EXTM3U\n#EXT-X-VERSION:3\n#EXT-X-TARGETDURATION:10\n";
//        for (String segment : segments) {
//            playlist += "#EXTINF:10,\n" + segment + "\n";
//        }
//        playlist += "#EXT-X-ENDLIST\n";
//
//        String finalPlaylist = playlist;
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=playlist.m3u8")
//                .body(outputStream -> {
//                    OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
//                    writer.write(finalPlaylist);
//                    writer.flush();
//
//                    // simulate streaming the video segments
//                    for (String segment : segments) {
//                        try (InputStream is = getClass().getClassLoader().getResourceAsStream("videos/" + segment)) {
//                            byte[] buffer = new byte[1024];
//                            int bytesRead;
//                            while ((bytesRead = is.read(buffer)) != -1) {
//                                outputStream.write(buffer, 0, bytesRead);
//                            }
//                            outputStream.flush();
//                        } catch (IOException e) {
//                            // handle exception
//                        }
//                    }
//                });
//    }


    @GetMapping(value = "/{videoId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> getVideo(@PathVariable String videoId) {
        try {
            String tepDir = "E:\\Data\\m3u8tmp\\";
            File file = new File(tepDir + videoId);
            // 无m3u8文件则生成
            if (!file.exists()) {
                videoService.generateM3U8(Integer.valueOf(videoId));
            }
            File videoFile = new File(tepDir + videoId + "\\" + videoId + "-.m3u8");
            InputStreamResource resource = null;
            resource = new InputStreamResource(new FileInputStream(videoFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/vnd.apple.mpegurl"))
                    .body(resource);
        } catch (FileNotFoundException e) {
            System.out.println("Error serving video stream: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping(value = "/{videoName}.ts", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> getVideoTS(@PathVariable String videoName) throws IOException {
        int lastIndex = videoName.lastIndexOf('-');
        String folder = "E:\\Data\\m3u8tmp\\";
        if (lastIndex != -1) {
            folder = folder + videoName.substring(0,lastIndex);
        }
        File videoFile = new File( folder + "\\" + videoName + ".ts");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(videoFile));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("video/mp2t"))
                .body(resource);
    }

}
