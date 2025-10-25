package com.npc.common.modular.money.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class MoneyAccountDto extends PageSearch {

    private Integer id;  // id 

    private Integer userId;  // 用户id 

    private String account;  // 账户 

    private String accountName;  // 账户名 

    private BigDecimal savings;  // 储蓄 

    private BigDecimal debt;  // 债务 

    private Integer status;  // 是否计入金额 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 最后更新时间 

}
