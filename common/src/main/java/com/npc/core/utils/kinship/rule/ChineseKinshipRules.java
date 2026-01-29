package com.npc.core.utils.kinship.rule;

import com.npc.core.utils.kinship.enums.RelationType;
import com.npc.core.utils.kinship.model.*;
import java.util.*;
import java.util.Arrays;
/**
 * @program: npcService
 * @description
 * @author: feiyang
 * @create: 2026/01/27 22:46
 **/
public class ChineseKinshipRules {

    public static final List<KinshipRule> RULES = Arrays.asList(
        // 一代
        rule(Arrays.asList(RelationType.FATHER), "父亲", 100),
        rule(Arrays.asList(RelationType.MOTHER), "母亲", 100),

        // 二代
        rule(Arrays.asList(RelationType.FATHER, RelationType.FATHER), "爷爷", 200),
        rule(Arrays.asList(RelationType.FATHER, RelationType.MOTHER), "奶奶", 200),
        rule(Arrays.asList(RelationType.MOTHER, RelationType.FATHER), "外公", 200),
        rule(Arrays.asList(RelationType.MOTHER, RelationType.MOTHER), "外婆", 200),

        // 父系兄弟
        rule(Arrays.asList(RelationType.FATHER, RelationType.BROTHER), "叔叔", 300),
        rule(Arrays.asList(RelationType.FATHER, RelationType.SISTER), "姑姑", 300),

        // 母系兄弟
        rule(Arrays.asList(RelationType.MOTHER, RelationType.BROTHER), "舅舅", 300),
        rule(Arrays.asList(RelationType.MOTHER, RelationType.SISTER), "姨妈", 300),

        // 堂
        rule(Arrays.asList(RelationType.FATHER, RelationType.BROTHER, RelationType.SON), "堂兄弟", 400),
        rule(Arrays.asList(RelationType.FATHER, RelationType.BROTHER, RelationType.DAUGHTER), "堂姐妹", 400),

        // 表
        rule(Arrays.asList(RelationType.MOTHER, RelationType.SISTER, RelationType.SON), "表兄弟", 400),
        rule(Arrays.asList(RelationType.MOTHER, RelationType.SISTER, RelationType.DAUGHTER), "表姐妹", 400)
    );

    private static KinshipRule rule(List<RelationType> path, String name, int priority) {
        List<KinshipAtom> atoms = new ArrayList<>();
        for (RelationType r : path) {
            atoms.add(r.atom);
        }
        return new KinshipRule(atoms, name, priority);
    }
}
