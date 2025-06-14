package com.npc.common.modular.subscribe.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 订阅表
 * </p>
 *
 * @author yangfei
 * @since 2025-02-24
 */
@Data
@TableName("t_subscribe")
public class Subscribe implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
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

}
