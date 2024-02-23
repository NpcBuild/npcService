package com.npc.common.modular.quartzJob.vo;

import com.npc.core.PageSearch;
import lombok.Data;

import java.util.Date;

/**
 * @author NPC
 * @description
 * @create 2023/9/6 22:46
 */
@Data
public class QuartzJobVO extends PageSearch {
    /**任务编号*/
    private Integer id;
    /**任务时间表达式*/
    private String cron;
    /**任务状态*/
    private String status;
    /**任务名称*/
    private String jobName;
    /** 创建时间*/
    private Date createTime;
    /** 更新时间*/
    private Date updateTime;
//    /**每页显示条数*/
//    private int pageSize=10;
//    /**当前页数*/
//    private int pageCurrent=1;
}
