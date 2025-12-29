package com.npc.common.modular.money.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @program: npcService
 * @description
 * @author: feiyang
 * @create: 2025/10/27 21:23
 **/
@Data
public class MoneyBudgetVO {
    private Integer id;
    // @ApiModelProperty(value = "所属用户ID，可关联用户表")
    private Integer userId;
    // @ApiModelProperty(value = "预算名称（如：本月餐饮预算）")
    private String budgetName;
    // @ApiModelProperty(value = "预算类别（对应账单类别：餐饮、交通、娱乐等）")
    private String category;
    // @ApiModelProperty(value = "预算上限金额")
    private BigDecimal amountLimit;
    // @ApiModelProperty(value = "当前已支出金额（可实时更新或统计时计算）")
    private BigDecimal amountSpent;
    // @ApiModelProperty(value = "预算开始日期")
    private LocalDate startDate;
    // @ApiModelProperty(value = "预算结束日期")
    private LocalDate endDate;
    // @ApiModelProperty(value = "预算周期类型（每周/每月/自定义）")
    private String periodType;
    // @ApiModelProperty(value = "是否自动延续下一个周期（0=否，1=是）")
    private Boolean autoExtend;
    // @ApiModelProperty(value = "提醒阈值（如0.8表示超出80%时提醒）")
    private BigDecimal alertThreshold;
    // @ApiModelProperty(value = "是否不计入整体汇总（0=计入，1=不计入）")
    private Boolean excludeFromSummary;

    // @ApiModelProperty(value = "备注信息")
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
