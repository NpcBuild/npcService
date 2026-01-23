package com.npc.common.modular.assets.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class AssetsFoodDto extends PageSearch {

    private Integer id;  // 食材扩展ID 

    private Integer assetId;  // 关联资产ID（t_assets.id） 

    private String foodCategory;  // 食材分类（蔬菜/水果/肉类/海鲜/乳制品/主食/调料） 

    private String origin;  // 产地 

    private String brand;  // 品牌 

    private String storageMethod;  // 存储方式（常温/冷藏/冷冻） 

    private LocalDate productionDate;  // 生产日期 

    private LocalDate expirationDate;  // 到期日期 

    private Integer freshLevel;  // 新鲜度评分（1-5） 

    private Boolean opened;  // 是否已开封 

    private BigDecimal remainingRatio;  // 剩余比例（%） 

    private String nutritionNotes;  // 营养信息 

    private String allergyInfo;  // 过敏原信息 

    private Boolean isFrozen;  // 是否冷冻 

    private String foodStatus;  // 食材状态 

    private String notes;  // 食材备注 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
