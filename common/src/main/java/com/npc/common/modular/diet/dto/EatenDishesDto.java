package com.npc.common.modular.diet.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 饮食记录
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Data
public class EatenDishesDto extends PageSearch {

    private Integer id; 

    private Integer userId;  // 用户 ID 

    private Integer recipesId;  // 菜谱 ID

    private String mealType;;  // 用餐类型

    private Integer tasteRating;  // 口味评分（1-5） 

    private LocalDate eatDate;  // 吃的时间 

    private String restaurant;  // 在哪吃的 

    private String notes;  // 备注（如感受、特点等） 

    private LocalDateTime createdAt; 

}
