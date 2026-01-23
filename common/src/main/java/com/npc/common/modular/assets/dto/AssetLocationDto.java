package com.npc.common.modular.assets.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class AssetLocationDto extends PageSearch {

    private Integer id;  // 资产位置ID 

    private String locationName;  // 位置名称（如 客厅/衣柜/冰箱） 

    private String locationType;  // 位置类型（房间/柜子/盒子/冰箱/冷冻） 

    private String parentLocation;  // 父级位置（如 冰箱-冷藏室） 

    private String detail;  // 详细位置说明（如 第二层左侧） 

    private String temperatureZone;  // 温区（常温/冷藏/冷冻） 

    private String notes;  // 位置备注 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
