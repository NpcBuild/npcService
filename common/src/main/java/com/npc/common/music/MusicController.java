package com.npc.common.music;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author NPC
 * @description 音乐控制层
 * @create 2023/4/9 19:47
 */
@CrossOrigin
@RestController
@RequestMapping("/music")
public class MusicController {

    private static final String MUSIC_DIR = "E:\\Data\\Computer\\Music\\";
    private static final String LYRIC_DIR = "E:\\Data\\Computer\\Music\\lyric\\";

    @GetMapping("/list")
    public ResponseEntity<Resource> getMusic() throws IOException {
        Path file = Paths.get(MUSIC_DIR);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getMusic(@PathVariable String filename) throws IOException {
        Path file = Paths.get(MUSIC_DIR + filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lyric/{filename}")
    public ResponseEntity<String> getLyric(@PathVariable String filename) throws IOException {
        Path file = Paths.get(LYRIC_DIR + filename);
        String content = new String(Files.readAllBytes(file), Charset.forName("UTF-8"));
        return ResponseEntity.ok(content);
    }
}
