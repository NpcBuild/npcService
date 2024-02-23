package com.npc.core.limit;

import com.npc.core.limit.entity.Limiter;

/**
 * 限流接口
 */
public interface LimiterManager {
    boolean tryAccess(Limiter limiter);
}
