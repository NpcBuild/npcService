package com.npc.common.modular.problem.dto;

import lombok.Data;

/**
 * @author NPC
 * @description
 * @create 2024/7/13 8:38
 */
@Data
public class ProblemDto {
    private Integer id;
    private String title;
    private String text;
    private String tags;
    private String status;
}
