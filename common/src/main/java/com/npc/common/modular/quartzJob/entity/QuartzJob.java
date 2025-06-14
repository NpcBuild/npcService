package com.npc.common.modular.quartzJob.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 定时任务表
 * </p>
 *
 * @author yangfei
 * @since 2023-09-06
 */
@Schema(name = "QuartzJob", description = "定时任务实体类")
@Data
@TableName("t_quartz_job")
public class QuartzJob implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "定时任务ID", example = "1001")
	@TableId(value="id", type= IdType.AUTO)
    private Integer id;

    /**
     * 定时执行
     */
    @Schema(description = "Cron表达式", example = "0 0 9 * * ?")
    private String cron;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称", example = "每日提醒任务")
    private String jobName;

    /**
     * 任务开启状态 0-关闭 2-开启
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
