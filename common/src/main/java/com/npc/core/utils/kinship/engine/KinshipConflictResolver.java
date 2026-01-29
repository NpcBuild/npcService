package com.npc.core.utils.kinship.engine;

import com.npc.core.utils.kinship.rule.KinshipRule;
import java.util.*;
/**
 * @program: npcService
 * @description
 * @author: feiyang
 * @create: 2026/01/27 22:48
 **/
public class KinshipConflictResolver {

    public KinshipRule resolve(List<KinshipRule> candidates) {
        return candidates.stream()
                .sorted(Comparator
                        .comparingInt((KinshipRule r) -> r.priority).reversed()
                        .thenComparingInt(r -> r.pattern.size()))
                .findFirst()
                .orElse(null);
    }
}
