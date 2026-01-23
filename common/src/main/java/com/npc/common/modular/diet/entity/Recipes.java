package com.npc.common.modular.diet.entity;

import java.io.Serializable;
//import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 菜谱
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Data
@TableName("diet_recipes")
public class Recipes implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
//    @ApiModelProperty(value = "id")
    private Integer id;



    /**
     * 菜名
     */
//    @ApiModelProperty(value = "菜名")
    private String name;



    /**
     * 菜系
     */
//    @ApiModelProperty(value = "菜系")
    private String cuisine;



    /**
     * 图片
     */
//    @ApiModelProperty(value = "图片")
    private String imgUrl;



    /**
     * 难度等级
     */
//    @ApiModelProperty(value = "难度等级")
    private String difficulty;



    /**
     * 简介
     */
//    @ApiModelProperty(value = "简介")
    private String description;



    /**
     * 学习日期
     */
//    @ApiModelProperty(value = "学习日期")
    private LocalDate learningDate;



    /**
     * 学习笔记
     */
//    @ApiModelProperty(value = "学习笔记")
    private String notes;
//    @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;

}
