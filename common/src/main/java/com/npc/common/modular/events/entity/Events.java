package com.npc.common.modular.events.entity;

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
 * 
 * </p>
 *
 * @author yangfei
 * @since 2025-06-27
 */
@Data
@TableName("t_events")
public class Events implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
    // @ApiModelProperty(value = "id")
    private Integer id;



    /**
     * 事件名称
     */
    // @ApiModelProperty(value = "事件名称")
    private String name;



    /**
     * 事件日期
     */
    // @ApiModelProperty(value = "事件日期")
    private LocalDateTime eventDate;



    /**
     * 事件类型：倒计时或纪念日
     */
    // @ApiModelProperty(value = "事件类型：倒计时或纪念日")
    private String type;



    /**
     * 事件描述
     */
    // @ApiModelProperty(value = "事件描述")
    private String description;



    /**
     * 是否提醒
     */
    // @ApiModelProperty(value = "是否提醒")
    private Boolean reminder;



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
