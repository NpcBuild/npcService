package com.npc.common.modular.money.dto;

import com.npc.core.PageSearch;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author NPC
 * @description
 * @create 2024/6/28 8:35
 */
@Data
public class MoneyDto extends PageSearch {
    private Integer id;

    /**
     * 交易描述
     */
    private String description;

    /**
     * 交易日期
     */
    private LocalDate date;

    /**
     * 交易类别（收入、花销等）
     */
    private String category;

    /**
     * 标签，用于关键词标记
     */
    private String tags;

    private String dateStartS;
    private String dateEndS;
}
