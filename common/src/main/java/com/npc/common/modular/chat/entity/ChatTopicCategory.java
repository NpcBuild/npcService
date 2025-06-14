package com.npc.common.modular.chat.entity;

import java.io.Serializable;
//import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 聊天话题分类表
 * </p>
 *
 * @author yangfei
 * @since 2025-05-05
 */
@Data
@TableName("t_chat_topic_category")
public class ChatTopicCategory implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
//    @ApiModelProperty(value = "主键")
    private Integer id;



    /**
     * 父级分类ID，0为顶级分类
     */
//    @ApiModelProperty(value = "父级分类ID，0为顶级分类")
    private Integer parentId;



    /**
     * 分类名称
     */
//    @ApiModelProperty(value = "分类名称")
    private String name;



    /**
     * 分类描述，可选
     */
//    @ApiModelProperty(value = "分类描述，可选")
    private String description;


    /**
     * 怎么做
     */
//    @ApiModelProperty(value = "怎么做")
    private String toDo;


    private Boolean important;  // 是否重要

    /**
     * 排序字段，越小越靠前
     */
//    @ApiModelProperty(value = "排序字段，越小越靠前")
    private Integer sortOrder;



    /**
     * 是否为叶子节点（即不能再细分）
     */
//    @ApiModelProperty(value = "是否为叶子节点（即不能再细分）")
    private Boolean isLeaf;



    /**
     * 状态（1:启用，0:禁用）
     */
//    @ApiModelProperty(value = "状态（1:启用，0:禁用）")
    private Boolean status;



    /**
     * 创建时间
     */
//    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;



    /**
     * 更新时间
     */
//    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

}
