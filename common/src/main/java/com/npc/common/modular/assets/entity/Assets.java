package com.npc.common.modular.assets.entity;

import java.io.Serializable;
//import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 物品表
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Data
@TableName("t_assets")
public class Assets implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 物品ID
     */
//    @ApiModelProperty(value = "物品ID")
    private Integer id;



    /**
     * 物品名称
     */
//    @ApiModelProperty(value = "物品名称")
    private String name;



    /**
     * 物品分类（如 电子产品、服装、家具）
     */
//    @ApiModelProperty(value = "物品分类（如 电子产品、服装、家具）")
    private String category;



    /**
     * 物品型号或规格（如 256GB、42码）
     */
//    @ApiModelProperty(value = "物品型号或规格（如 256GB、42码）")
    private String model;



    /**
     * 购买日期
     */
//    @ApiModelProperty(value = "购买日期")
    private LocalDate purchaseDate;



    /**
     * 购买价格
     */
//    @ApiModelProperty(value = "购买价格")
    private BigDecimal price;



    /**
     * 数量
     */
//    @ApiModelProperty(value = "数量")
    private Integer quantity;



    /**
     * 单位（如千克、升、个）
     */
//    @ApiModelProperty(value = "单位（如千克、升、个）")
    private String unit;



    /**
     * 物品存放位置（如 客厅、书房）
     */
//    @ApiModelProperty(value = "物品存放位置（如 客厅、书房）")
    private String location;



    /**
     * 物品状态
     */
//    @ApiModelProperty(value = "物品状态")
    private String status;



    /**
     * 备注
     */
//    @ApiModelProperty(value = "备注")
    private String notes;



    /**
     * 保质期（食品、化妆品等）
     */
//    @ApiModelProperty(value = "保质期（食品、化妆品等）")
    private LocalDateTime expirationDate;



    /**
     * 物品图片
     */
//    @ApiModelProperty(value = "物品图片")
    private String imageUrl;



    /**
     * 记录创建时间
     */
//    @ApiModelProperty(value = "记录创建时间")
    private LocalDateTime createTime;



    /**
     * 记录更新时间
     */
//    @ApiModelProperty(value = "记录更新时间")
    private LocalDateTime updateTime;

}
