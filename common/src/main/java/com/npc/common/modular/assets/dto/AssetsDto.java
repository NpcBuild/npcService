package com.npc.common.modular.assets.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class AssetsDto extends PageSearch {

    private Integer id;  // 物品ID 

    private String name;  // 物品名称 

    private String category;  // 物品分类（如 电子产品、服装、家具） 

    private String model;  // 物品型号或规格（如 256GB、42码） 

    private LocalDate purchaseDate;  // 购买日期 

    private BigDecimal price;  // 购买价格 

    private Integer quantity;  // 数量 

    private String unit;  // 单位（如千克、升、个） 

    private String location;  // 物品存放位置（如 客厅、书房） 

    private String status;  // 物品状态 

    private String notes;  // 备注 

    private LocalDateTime expirationDate;  // 保质期（食品、化妆品等） 

    private String imageUrl;  // 物品图片 

    private LocalDateTime createTime;  // 记录创建时间 

    private LocalDateTime updateTime;  // 记录更新时间 

}
