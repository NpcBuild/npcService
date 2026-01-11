package com.npc.common.modular.assets.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class AssetsClothingDto extends PageSearch {

    private Integer id;  // 衣物扩展ID 

    private Integer assetId;  // 关联的资产ID（t_assets.id） 

    private String clothingCategory;  // 衣物分类（上衣/下装/鞋子/外套/配饰） 

    private String season;  // 适用季节（春季/夏季/秋季/冬季） 

    private String colorStyle;  // 颜色风格（冷色/暖色/中性） 

    private String color;  // 具体颜色（黑色/白色/蓝色等） 

    private String size;  // 尺码（S/M/L/42等） 

    private String fitType;  // 版型（修身/宽松/标准） 

    private String material;  // 材质（棉/羊毛/涤纶等） 

    private String thickness;  // 厚度（薄/中/厚） 

    private Integer wearFrequency;  // 穿着频率（估算值） 

    private LocalDate lastWornDate;  // 最近一次穿着日期 

    private String styleTags;  // 风格标签（通勤,休闲,运动,正式） 

    private String occasion;  // 适用场合（上班/约会/运动） 

    private String washMethod;  // 洗护方式（手洗/机洗/干洗） 

    private String careNotes;  // 护理备注 

    private String clothingStatus;  // 衣物状态（正常/闲置/过时/损坏） 

    private String notes;  // 衣物专属备注 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
