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
 * 金额记录点
 * </p>
 *
 * @author yangfei
 * @since 2025-08-20
 */
@Data
@TableName("t_money_point")
public class MoneyPoint implements Serializable {

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
     * 记录时间点
     */
    // @ApiModelProperty(value = "记录时间点")
    private LocalDateTime dateTime;



    /**
     * 金额
     */
    // @ApiModelProperty(value = "金额")
    private BigDecimal money;



    /**
     * 1：记录点；2：历史点
     */
    // @ApiModelProperty(value = "1：记录点；2：历史点")
    private String type;



    /**
     * 资产金额
     */
    // @ApiModelProperty(value = "资产金额")
    private BigDecimal assets;



    /**
     * 债务金额
     */
    // @ApiModelProperty(value = "债务金额")
    private BigDecimal debt;



    /**
     * 状态
     */
    // @ApiModelProperty(value = "状态")
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
