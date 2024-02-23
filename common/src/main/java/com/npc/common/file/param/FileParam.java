package com.npc.common.file.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author NPC
 * @description
 * @create 2023/3/5 18:33
 */
@Data
public class FileParam {
    private String path;
    private MultipartFile file;
    private int chunkNumber;
    private int chunkSize;
    private int currentChunkSize;
    private int totalSize;
    private String identifier;
    private String filename;
    private String relativePath;
    private int totalChunks;
    private String page;

}
