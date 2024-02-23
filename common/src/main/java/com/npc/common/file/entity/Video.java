package com.npc.common.file.entity;

import java.io.Serializable;
//import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2023-04-08
 */
@Data
@TableName("t_video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty(value = "id")
    private Integer id;



    /**
     * 类型
     */
//    @ApiModelProperty(value = "类型")
    private String type;



    /**
     * 文件名
     */
//    @ApiModelProperty(value = "文件名")
    private String title;



    /**
     * 文件存储地址
     */
//    @ApiModelProperty(value = "文件存储地址")
    private String address;



    /**
     * 文件大小,KB
     */
//    @ApiModelProperty(value = "文件大小,KB")
    private Double size;



    /**
     * md5
     */
//    @ApiModelProperty(value = "md5")
    private String md5;

}
