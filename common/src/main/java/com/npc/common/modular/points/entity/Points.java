package com.npc.common.modular.points.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 用户积分余额表
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Data
public class Points implements Serializable {

    private static final long serialVersionUID = 1L;




    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "主键ID")
    private Long id;



    /**
     * 用户ID
     */
    // @ApiModelProperty(value = "用户ID")
    private Long userId;



    /**
     * 当前积分总额
     */
    // @ApiModelProperty(value = "当前积分总额")
    private Integer totalPoints;



    /**
     * 最后更新时间
     */
    // @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updatedAt;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

}
