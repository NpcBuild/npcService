package com.npc.common.modular.problem.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author NPC
 * @description
 * @create 2025/10/16 21:45
 */
@Data
public class UploadDto {
    private String id;
    private String fileName;
    private List<MultipartFile> pic;
    private MultipartFile file1;
    private MultipartFile file2;
    private MultipartFile file3;
    private MultipartFile file4;
    private MultipartFile file5;
    private MultipartFile file6;
    private MultipartFile file7;
    private MultipartFile file8;
    private MultipartFile file9;
}
