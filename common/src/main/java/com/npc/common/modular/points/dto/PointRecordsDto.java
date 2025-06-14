package com.npc.common.modular.points.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class PointRecordsDto extends PageSearch {

    private Long id;  // 主键ID 

    private Long userId;  // 用户ID 

    private String changeType;  // 变动类型（收入/支出） 

    private Integer points;  // 变动的积分值，正数或负数 

    private Integer beforePoints;  // 变更前积分 

    private Integer afterPoints;  // 变更后积分 

    private String source;  // 变动来源（如：签到、兑换、任务、后台调整） 

    private String remark;  // 备注说明 

    private LocalDateTime createdAt;  // 创建时间 

}
