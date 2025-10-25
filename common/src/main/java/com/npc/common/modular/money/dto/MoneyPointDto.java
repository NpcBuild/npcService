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
 * 金额记录点
 * </p>
 *
 * @author yangfei
 * @since 2025-08-20
 */
@Data
public class MoneyPointDto extends PageSearch {

    private Integer id;  // id 

    private Integer userId;  // 用户id 

    private LocalDateTime dateTime;  // 记录时间点 

    private BigDecimal money;  // 金额 

    private String type;  // 1：记录点；2：历史点 

    private BigDecimal assets;  // 资产金额 

    private BigDecimal debt;  // 债务金额 

    private Integer status;  // 状态 

    private LocalDateTime createdAt;  // 创建时间 

    private LocalDateTime updatedAt;  // 最后更新时间 

}
