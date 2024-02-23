package com.npc.common.modular.money.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangfei
 * @since 2023-12-25
 */
@Data
@TableName("t_money")
public class Money implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 交易唯一标识符，自增长
     */
	@TableId(value="id", type= IdType.AUTO)
    private Integer id;



    /**
     * 交易描述
     */
    private String description;



    /**
     * 交易金额
     */
    private BigDecimal amount;



    /**
     * 交易日期
     */
    private LocalDate date;



    /**
     * 付款时间
     */
    private LocalDateTime paymentTime;



    /**
     * 交易类别（收入、花销等）
     */
    private String category;



    /**
     * 标签，用于关键词标记
     */
    private String tags;



    /**
     * 备注
     */
    private String notes;



    /**
     * 花销发生的地点
     */
    private String expenseLocation;



    /**
     * 是否为定期交易
     */
    private Boolean recurringTransaction;

}
