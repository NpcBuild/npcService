package com.npc.common.modular.diet.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class FoodItemsDto extends PageSearch {

    private Integer id;  // 食物条目ID 

    private Integer eatenDishId;  // 关联的用餐记录ID（diet_eaten_dishes.id） 

    private String name;  // 食物名称 

    private String category;  // 食物分类，如主食/蔬菜/肉类 

    private Double calories;  // 热量 kcal 

    private Double protein;  // 蛋白质 g 

    private Double carbs;  // 碳水 g 

    private Double fat;  // 脂肪 g 

    private Double fiber;  // 膳食纤维 g 

    private Double sugar;  // 糖 g 

    private Double sodium;  // 钠 mg 

    private Double quantity;  // 数量（单位视具体食物而定） 

    private String unit;  // 单位，如份/克/毫升 

    private String notes;  // 备注 

    private LocalDateTime createdAt;  // 创建时间 

}
