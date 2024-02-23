package com.npc.common.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2023-05-05
 */
@Data
@TableName("t_file_slice")
public class FileSlice implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;



    /**
     * 文件名
     */
    private String name;



    /**
     * 文件大小
     */
    private Long size;



    /**
     * 文件的MD5值
     */
    private String md5;



    /**
     * 已上传的分片数
     */
    private Integer uploadedChunks;



    /**
     * 当前分片的序号
     */
    private Integer chunkIndex;



    /**
     * 总的分片数
     */
    private Integer totalChunks;

}
