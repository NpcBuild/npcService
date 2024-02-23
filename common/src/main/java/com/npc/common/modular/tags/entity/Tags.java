package com.npc.common.modular.tags.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author yangfei
 * @since 2023-12-20
 */
@Data
@TableName("t_tags")
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * ID
     */
	@TableId(value="id", type= IdType.AUTO)
    private Integer id;



    /**
     * 父级ID
     */
    private Integer parentId;



    /**
     * 组ID
     */
    private Integer groupId;



    /**
     * 名称
     */
    private String name;



    /**
     * 类型
     */
    private String type;



    /**
     * 显示颜色
     */
    private String color;



    /**
     * 描述
     */
    private String description;



    /**
     * 使用次数
     */
    private Integer usageCount;



    /**
     * 图标链接
     */
    private String iconUrl;



    /**
     * 是否是推荐
     */
    private Boolean isFeatured;



    /**
     * 是否公共可见
     */
    private Boolean isPublic;



    /**
     * 来源
     */
    private String source;



    /**
     * 所有者用户ID
     */
    private Integer ownerUserId;



    /**
     * 自定义字段
     */
    private String customFields;



    /**
     * 权重
     */
    private Double weight;



    /**
     * 用户评级
     */
    private Integer userRating;



    /**
     * 排序优先级
     */
    private Integer priority;



    /**
     * 相关标签
     */
    private String relatedTags;



    /**
     * 创建时间
     */
    private LocalDateTime createdAt;



    /**
     * 最后更新时间
     */
    private LocalDateTime updatedAt;



    /**
     * 别名
     */
    private String aliases;

}
