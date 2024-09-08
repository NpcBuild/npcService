package com.npc.common.todo.vo;

import com.npc.common.modular.quartzJob.vo.QuartzJobVO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author NPC
 * @description
 * @create 2023/9/10 8:36
 */
@Data
public class TodoVO extends QuartzJobVO {
    /**
     * 主键ID
     */
    private Integer id;
    private String ids;

    /**
     * 任务名称
     */
    private String todoName;

    /**
     * 任务类型 1-todo 2-习惯
     */
    private String type;

    /**
     * 标签
     */
    private String tag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 任务状态 0-未开始 1-进行中 2-已完成
     */
    private String status;

    /**
     * 定时任务表主键
     */
    private Integer quartzId;
    private Integer problemId; //问题表主键

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime endTime;

    private Boolean done; // 任务是否完成
    private String date; // 查询用
    private String startDate; // 查询用
    private String endDate; // 查询用

}
