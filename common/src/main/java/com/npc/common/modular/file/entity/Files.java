package com.npc.common.modular.file.entity;

import java.io.Serializable;


import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 缓存文件
 * </p>
 *
 * @author yangfei
 * @since 2023-08-05
 */
@Data
@TableName("t_file")
public class Files implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 文件唯一标识
     */
    private Integer id;
    /**
     * 父级文件夹id
     */
    private Integer parentDirId;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件大小
     */
    private Double size;
    /**
     * 文件后缀类型
     */
    private String type;
    /**
     * 文件md5
     */
    private String md5;
    /**
     * 文件缩略图Base64
     */
    private byte[] icon;
    /**
     * 创建时间
     */
    private LocalDateTime creTime;
    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;
}
