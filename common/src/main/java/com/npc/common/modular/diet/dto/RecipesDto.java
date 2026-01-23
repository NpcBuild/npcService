package com.npc.common.modular.diet.dto;

import com.npc.core.PageSearch;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜谱
 * </p>
 *
 * @author yangfei
 * @since 2025-04-06
 */
@Data
public class RecipesDto extends PageSearch {

    private Integer id; 

    private String name;  // 菜名 

    private String cuisine;  // 菜系 

    private String imgUrl;  // 图片 

    private String difficulty;  // 难度等级 

    private String description;  // 简介 

    private LocalDate learningDate;  // 学习日期 

    private String notes;  // 学习笔记 

    private LocalDateTime createdAt; 

}
