package com.npc.common.modular.subscribe.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订阅表
 * </p>
 *
 * @author yangfei
 * @since 2025-02-24
 */
@Data
public class SubscribeVO implements Serializable {

    private Integer id;

    /**
     * 订阅内容
     */
    private String subContent;

    /**
     * 订阅用户
     */
    private Integer userId;

    /**
     * 废弃-每次付款的具体日期
     */
    private String paymentDate;

    /**
     * 废弃-付款的周期
     */
    private String paymentCycle;

    /**
     * 付款时间
     */
    private String payCron;

    /**
     * 订阅所需支付的金额
     */
    private String subAmount;

    /**
     * 交易类别
     */
    private String category;

    /**
     * 订阅开始生效的日期
     */
    private LocalDateTime startDate;

    /**
     * 订阅结束的日期
     */
    private LocalDateTime endDate;

    /**
     * 订阅状态
     */
    private String subStatus;

    /**
     * 备注
     */
    private String notes;

    private LocalDateTime subTime; // 订阅时间

    /**
     * 剩余还款周期数
     */
    private Long remainingPeriods;

}
