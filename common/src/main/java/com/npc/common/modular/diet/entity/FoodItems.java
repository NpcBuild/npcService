package com.npc.common.modular.diet.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 每餐食物条目表
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@Data
@TableName("diet_food_items")
public class FoodItems implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 食物条目ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "食物条目ID")
    private Integer id;



    /**
     * 关联的用餐记录ID（diet_eaten_dishes.id）
     */
    // @ApiModelProperty(value = "关联的用餐记录ID（diet_eaten_dishes.id）")
    private Integer eatenDishId;



    /**
     * 食物名称
     */
    // @ApiModelProperty(value = "食物名称")
    private String name;



    /**
     * 食物分类，如主食/蔬菜/肉类
     */
    // @ApiModelProperty(value = "食物分类，如主食/蔬菜/肉类")
    private String category;



    /**
     * 热量 kcal
     */
    // @ApiModelProperty(value = "热量 kcal")
    private Double calories;



    /**
     * 蛋白质 g
     */
    // @ApiModelProperty(value = "蛋白质 g")
    private Double protein;



    /**
     * 碳水 g
     */
    // @ApiModelProperty(value = "碳水 g")
    private Double carbs;



    /**
     * 脂肪 g
     */
    // @ApiModelProperty(value = "脂肪 g")
    private Double fat;



    /**
     * 膳食纤维 g
     */
    // @ApiModelProperty(value = "膳食纤维 g")
    private Double fiber;



    /**
     * 糖 g
     */
    // @ApiModelProperty(value = "糖 g")
    private Double sugar;



    /**
     * 钠 mg
     */
    // @ApiModelProperty(value = "钠 mg")
    private Double sodium;



    /**
     * 数量（单位视具体食物而定）
     */
    // @ApiModelProperty(value = "数量（单位视具体食物而定）")
    private Double quantity;



    /**
     * 单位，如份/克/毫升
     */
    // @ApiModelProperty(value = "单位，如份/克/毫升")
    private String unit;



    /**
     * 备注
     */
    // @ApiModelProperty(value = "备注")
    private String notes;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

}
