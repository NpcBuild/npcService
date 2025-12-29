package com.npc.common.modular.money.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
@TableName("t_money_budget")
public class MoneyBudget implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 预算唯一标识符，自增长
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "预算唯一标识符，自增长")
    private Integer id;



    /**
     * 所属用户ID，可关联用户表
     */
    // @ApiModelProperty(value = "所属用户ID，可关联用户表")
    private Integer userId;



    /**
     * 预算名称（如：本月餐饮预算）
     */
    // @ApiModelProperty(value = "预算名称（如：本月餐饮预算）")
    private String budgetName;



    /**
     * 预算类别（对应账单类别：餐饮、交通、娱乐等）
     */
    // @ApiModelProperty(value = "预算类别（对应账单类别：餐饮、交通、娱乐等）")
    private String category;



    /**
     * 预算上限金额
     */
    // @ApiModelProperty(value = "预算上限金额")
    private BigDecimal amountLimit;



    /**
     * 当前已支出金额（可实时更新或统计时计算）
     */
    // @ApiModelProperty(value = "当前已支出金额（可实时更新或统计时计算）")
    private BigDecimal amountSpent;



    /**
     * 预算开始日期
     */
    // @ApiModelProperty(value = "预算开始日期")
    private LocalDate startDate;



    /**
     * 预算结束日期
     */
    // @ApiModelProperty(value = "预算结束日期")
    private LocalDate endDate;



    /**
     * 预算周期类型（每周/每月/自定义）
     */
    // @ApiModelProperty(value = "预算周期类型（每周/每月/自定义）")
    private String periodType;



    /**
     * 是否自动延续下一个周期（0=否，1=是）
     */
    // @ApiModelProperty(value = "是否自动延续下一个周期（0=否，1=是）")
    private Boolean autoExtend;



    /**
     * 提醒阈值（如0.8表示超出80%时提醒）
     */
    // @ApiModelProperty(value = "提醒阈值（如0.8表示超出80%时提醒）")
    private BigDecimal alertThreshold;



    /**
     * 是否不计入整体汇总（0=计入，1=不计入）
     */
    // @ApiModelProperty(value = "是否不计入整体汇总（0=计入，1=不计入）")
    private Boolean excludeFromSummary;



    /**
     * 备注信息
     */
    // @ApiModelProperty(value = "备注信息")
    private String notes;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;



    /**
     * 更新时间
     */
    // @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

}
