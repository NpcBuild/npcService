package com.npc.common.modular.assets.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 衣物资产扩展表
 * </p>
 *
 * @author yangfei
 * @since 2026-01-04
 */
@Data
@TableName("t_assets_clothing")
public class AssetsClothing implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 衣物扩展ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "衣物扩展ID")
    private Integer id;



    /**
     * 关联的资产ID（t_assets.id）
     */
    // @ApiModelProperty(value = "关联的资产ID（t_assets.id）")
    private Integer assetId;



    /**
     * 衣物分类（上衣/下装/鞋子/外套/配饰）
     */
    // @ApiModelProperty(value = "衣物分类（上衣/下装/鞋子/外套/配饰）")
    private String clothingCategory;



    /**
     * 适用季节（春季/夏季/秋季/冬季）
     */
    // @ApiModelProperty(value = "适用季节（春季/夏季/秋季/冬季）")
    private String season;



    /**
     * 颜色风格（冷色/暖色/中性）
     */
    // @ApiModelProperty(value = "颜色风格（冷色/暖色/中性）")
    private String colorStyle;



    /**
     * 具体颜色（黑色/白色/蓝色等）
     */
    // @ApiModelProperty(value = "具体颜色（黑色/白色/蓝色等）")
    private String color;



    /**
     * 尺码（S/M/L/42等）
     */
    // @ApiModelProperty(value = "尺码（S/M/L/42等）")
    private String size;



    /**
     * 版型（修身/宽松/标准）
     */
    // @ApiModelProperty(value = "版型（修身/宽松/标准）")
    private String fitType;



    /**
     * 材质（棉/羊毛/涤纶等）
     */
    // @ApiModelProperty(value = "材质（棉/羊毛/涤纶等）")
    private String material;



    /**
     * 厚度（薄/中/厚）
     */
    // @ApiModelProperty(value = "厚度（薄/中/厚）")
    private String thickness;



    /**
     * 穿着频率（估算值）
     */
    // @ApiModelProperty(value = "穿着频率（估算值）")
    private Integer wearFrequency;



    /**
     * 最近一次穿着日期
     */
    // @ApiModelProperty(value = "最近一次穿着日期")
    private LocalDate lastWornDate;



    /**
     * 风格标签（通勤,休闲,运动,正式）
     */
    // @ApiModelProperty(value = "风格标签（通勤,休闲,运动,正式）")
    private String styleTags;



    /**
     * 适用场合（上班/约会/运动）
     */
    // @ApiModelProperty(value = "适用场合（上班/约会/运动）")
    private String occasion;



    /**
     * 洗护方式（手洗/机洗/干洗）
     */
    // @ApiModelProperty(value = "洗护方式（手洗/机洗/干洗）")
    private String washMethod;



    /**
     * 护理备注
     */
    // @ApiModelProperty(value = "护理备注")
    private String careNotes;



    /**
     * 衣物状态（正常/闲置/过时/损坏）
     */
    // @ApiModelProperty(value = "衣物状态（正常/闲置/过时/损坏）")
    private String clothingStatus;



    /**
     * 衣物专属备注
     */
    // @ApiModelProperty(value = "衣物专属备注")
    private String notes;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;



    /**
     * 更新时间
     */
    // @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

}
