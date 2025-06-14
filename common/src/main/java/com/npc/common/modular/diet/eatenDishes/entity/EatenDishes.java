package com.npc.common.modular.diet.eatenDishes.entity;

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
 * 饮食记录
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Data
@TableName("diet_eaten_dishes")
public class EatenDishes implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
//    @ApiModelProperty(value = "id")
    private Integer id;



    /**
     * 用户 ID
     */
//    @ApiModelProperty(value = "用户 ID")
    private Integer userId;



    /**
     * 菜谱 ID
     */
//    @ApiModelProperty(value = "菜谱 ID")
    private Integer recipesId;



    /**
     * 口味评分（1-5）
     */
//    @ApiModelProperty(value = "口味评分（1-5）")
    private Integer tasteRating;



    /**
     * 吃的时间
     */
//    @ApiModelProperty(value = "吃的时间")
    private LocalDate eatDate;



    /**
     * 在哪吃的
     */
//    @ApiModelProperty(value = "在哪吃的")
    private String restaurant;



    /**
     * 备注（如感受、特点等）
     */
//    @ApiModelProperty(value = "备注（如感受、特点等）")
    private String notes;
//    @ApiModelProperty(value = "createdAt")
    private LocalDateTime createdAt;

}
