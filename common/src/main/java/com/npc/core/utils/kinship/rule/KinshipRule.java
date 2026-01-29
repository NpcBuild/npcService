package com.npc.core.utils.kinship.rule;

import com.npc.core.utils.kinship.model.KinshipAtom;
import java.util.List;
/**
 * @program: npcService
 * @description 规则
 * @author: feiyang
 * @create: 2026/01/27 22:45
 **/
public class KinshipRule {

    public List<KinshipAtom> pattern;
    public String name;
    public int priority;

    public KinshipRule(List<KinshipAtom> pattern, String name, int priority) {
        this.pattern = pattern;
        this.name = name;
        this.priority = priority;
    }
}