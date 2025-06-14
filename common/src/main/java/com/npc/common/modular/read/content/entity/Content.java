package com.npc.common.modular.read.content.entity;

import java.io.Serializable;
//import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Year;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Data
@TableName("read_content")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
//    @ApiModelProperty(value = "id")
    private Integer id;



    /**
     * 类型：书籍 / 电影
     */
//    @ApiModelProperty(value = "类型：书籍 / 电影")
    private String type;



    /**
     * 书名 / 电影名
     */
//    @ApiModelProperty(value = "书名 / 电影名")
    private String title;



    /**
     * 创作者
     */
//    @ApiModelProperty(value = "创作者")
    private String author;



    /**
     * 出版 / 上映年份
     */
//    @ApiModelProperty(value = "出版 / 上映年份")
    private Year releaseYear;



    /**
     * 类别（如 科幻 / 悬疑）
     */
//    @ApiModelProperty(value = "类别（如 科幻 / 悬疑）")
    private String genre;



    /**
     * 封面图片 URL
     */
//    @ApiModelProperty(value = "封面图片 URL")
    private String coverUrl;



    /**
     * 简介
     */
//    @ApiModelProperty(value = "简介")
    private String description;



    /**
     * 状态（想读 / 已读）
     */
//    @ApiModelProperty(value = "状态（想读 / 已读）")
    private String status;
//    @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;

}
