package com.npc.common.modular.money.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 金额账户
 * </p>
 *
 * @author yangfei
 * @since 2025-08-20
 */
@Data
@TableName("t_money_account")
public class MoneyAccount implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "id")
    private Integer id;



    /**
     * 用户id
     */
    // @ApiModelProperty(value = "用户id")
    private Integer userId;



    /**
     * 账户
     */
    // @ApiModelProperty(value = "账户")
    private String account;



    /**
     * 账户名
     */
    // @ApiModelProperty(value = "账户名")
    private String accountName;



    /**
     * 储蓄
     */
    // @ApiModelProperty(value = "储蓄")
    private BigDecimal savings;



    /**
     * 债务
     */
    // @ApiModelProperty(value = "债务")
    private BigDecimal debt;



    /**
     * 是否计入金额
     */
    // @ApiModelProperty(value = "是否计入金额")
    private Integer status;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;



    /**
     * 最后更新时间
     */
    // @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updatedAt;

}
