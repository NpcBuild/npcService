package com.npc.common.modular.assets.entity;

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
 * 资产位置表
 * </p>
 *
 * @author yangfei
 * @since 2026-01-23
 */
@Data
@TableName("t_asset_location")
public class AssetLocation implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 资产位置ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "资产位置ID")
    private Integer id;



    /**
     * 位置名称（如 客厅/衣柜/冰箱）
     */
    // @ApiModelProperty(value = "位置名称（如 客厅/衣柜/冰箱）")
    private String locationName;



    /**
     * 位置类型（房间/柜子/盒子/冰箱/冷冻）
     */
    // @ApiModelProperty(value = "位置类型（房间/柜子/盒子/冰箱/冷冻）")
    private String locationType;



    /**
     * 父级位置（如 冰箱-冷藏室）
     */
    // @ApiModelProperty(value = "父级位置（如 冰箱-冷藏室）")
    private String parentLocation;



    /**
     * 详细位置说明（如 第二层左侧）
     */
    // @ApiModelProperty(value = "详细位置说明（如 第二层左侧）")
    private String detail;



    /**
     * 温区（常温/冷藏/冷冻）
     */
    // @ApiModelProperty(value = "温区（常温/冷藏/冷冻）")
    private String temperatureZone;



    /**
     * 位置备注
     */
    // @ApiModelProperty(value = "位置备注")
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
