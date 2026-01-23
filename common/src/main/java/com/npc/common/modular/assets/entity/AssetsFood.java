package com.npc.common.modular.assets.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 食材资产扩展表
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@Data
@TableName("t_assets_food")
public class AssetsFood implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 食材扩展ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "食材扩展ID")
    private Integer id;



    /**
     * 关联资产ID（t_assets.id）
     */
    // @ApiModelProperty(value = "关联资产ID（t_assets.id）")
    private Integer assetId;



    /**
     * 食材分类（蔬菜/水果/肉类/海鲜/乳制品/主食/调料）
     */
    // @ApiModelProperty(value = "食材分类（蔬菜/水果/肉类/海鲜/乳制品/主食/调料）")
    private String foodCategory;



    /**
     * 产地
     */
    // @ApiModelProperty(value = "产地")
    private String origin;



    /**
     * 品牌
     */
    // @ApiModelProperty(value = "品牌")
    private String brand;



    /**
     * 存储方式（常温/冷藏/冷冻）
     */
    // @ApiModelProperty(value = "存储方式（常温/冷藏/冷冻）")
    private String storageMethod;



    /**
     * 生产日期
     */
    // @ApiModelProperty(value = "生产日期")
    private LocalDate productionDate;



    /**
     * 到期日期
     */
    // @ApiModelProperty(value = "到期日期")
    private LocalDate expirationDate;



    /**
     * 新鲜度评分（1-5）
     */
    // @ApiModelProperty(value = "新鲜度评分（1-5）")
    private Integer freshLevel;



    /**
     * 是否已开封
     */
    // @ApiModelProperty(value = "是否已开封")
    private Boolean opened;



    /**
     * 剩余比例（%）
     */
    // @ApiModelProperty(value = "剩余比例（%）")
    private BigDecimal remainingRatio;



    /**
     * 营养信息
     */
    // @ApiModelProperty(value = "营养信息")
    private String nutritionNotes;



    /**
     * 过敏原信息
     */
    // @ApiModelProperty(value = "过敏原信息")
    private String allergyInfo;



    /**
     * 是否冷冻
     */
    // @ApiModelProperty(value = "是否冷冻")
    private Boolean isFrozen;



    /**
     * 食材状态
     */
    // @ApiModelProperty(value = "食材状态")
    private String foodStatus;



    /**
     * 食材备注
     */
    // @ApiModelProperty(value = "食材备注")
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
