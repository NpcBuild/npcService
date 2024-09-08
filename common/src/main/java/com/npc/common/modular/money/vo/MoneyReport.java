package com.npc.common.modular.money.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author NPC
 * @description
 * @create 2024/3/27 21:13
 */
@Data
public class MoneyReport {
    private BigDecimal income; // 收入
    private BigDecimal expenditure; // 支出
    private BigDecimal balance; // 结余
    private BigDecimal average; // 平均
}
