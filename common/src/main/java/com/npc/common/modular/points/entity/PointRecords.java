package com.npc.common.modular.points.entity;

import java.io.Serializable;
// import io.swagger.annotations.ApiModelProperty;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * <p>
 * 积分变动记录表
 * </p>
 *
 * @author yangfei
 * @since 2025-06-07
 */
@Data
@TableName("point_records")
public class PointRecords implements Serializable {

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
     * 变动类型（收入/支出）
     */
    // @ApiModelProperty(value = "变动类型（收入/支出）")
    private String changeType;



    /**
     * 变动的积分值，正数或负数
     */
    // @ApiModelProperty(value = "变动的积分值，正数或负数")
    private Integer points;



    /**
     * 变更前积分
     */
    // @ApiModelProperty(value = "变更前积分")
    private Integer beforePoints;



    /**
     * 变更后积分
     */
    // @ApiModelProperty(value = "变更后积分")
    private Integer afterPoints;



    /**
     * 变动来源（如：签到、兑换、任务、后台调整）
     */
    // @ApiModelProperty(value = "变动来源（如：签到、兑换、任务、后台调整）")
    private String source;



    /**
     * 备注说明
     */
    // @ApiModelProperty(value = "备注说明")
    private String remark;



    /**
     * 创建时间
     */
    // @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

}
