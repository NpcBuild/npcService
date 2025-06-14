package com.npc.common.modular.points.dto;

import java.time.LocalDateTime;

import com.npc.core.PageSearch;
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
public class PointsDto extends PageSearch {

    private Long id;  // 主键ID 

    private Long userId;  // 用户ID 

    private Integer totalPoints;  // 当前积分总额 

    private LocalDateTime updatedAt;  // 最后更新时间 

    private LocalDateTime createdAt;  // 创建时间 

}
