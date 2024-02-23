package com.npc.common.modular.file.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author NPC
 * @description
 * @create 2023/8/5 10:17
 */
@Data
public class FileVO {
    private Integer id;
    private String name; // 文件名称
    private String path; // 文件路径
    private Double size; // 文件大小
    private String type; // 文件后缀类型
    private LocalDateTime creTime; // 创建时间
    private LocalDateTime uploadTime; // 上传时间

    private String img; // 文件图标显示
}
