package com.npc.common.modular.money.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
import lombok.Data;

/**
 * <p>
 * 用户预算管理表
 * </p>
 *
 * @author yangfei
 * @since 2025-10-27
 */
@Data
public class MoneyBudgetDto extends PageSearch {

    private Integer id;  // 预算唯一标识符，自增长 

    private Integer userId;  // 所属用户ID，可关联用户表 

    private String budgetName;  // 预算名称（如：本月餐饮预算） 

    private String category;  // 预算类别（对应账单类别：餐饮、交通、娱乐等） 

    private BigDecimal amountLimit;  // 预算上限金额 

    private BigDecimal amountSpent;  // 当前已支出金额（可实时更新或统计时计算） 

    private LocalDate startDate;  // 预算开始日期 

    private LocalDate endDate;  // 预算结束日期 

    private String periodType;  // 预算周期类型（每周/每月/自定义） 

    private Boolean autoExtend;  // 是否自动延续下一个周期（0=否，1=是） 

    private BigDecimal alertThreshold;  // 提醒阈值（如0.8表示超出80%时提醒） 

    private Boolean excludeFromSummary;  // 是否不计入整体汇总（0=计入，1=不计入） 

    private String notes;  // 备注信息 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 更新时间 

}
