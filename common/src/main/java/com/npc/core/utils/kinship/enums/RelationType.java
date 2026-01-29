package com.npc.core.utils.kinship.enums;

import com.npc.core.utils.kinship.model.*;
/**
 * @program: npcService
 * @description 关系类型
 * @author: feiyang
 * @create: 2026/01/27 22:43
 **/
public enum RelationType {

    // FATHER
    FATHER("A1", new KinshipAtom(GenerationOffset.UP, Gender.MALE, Lineage.PATERNAL)),
    // MOTHER
    MOTHER("A2", new KinshipAtom(GenerationOffset.UP, Gender.FEMALE, Lineage.MATERNAL)),

    // BROTHER - 对应 A13 和 A14
    BROTHER("A13,A14", new KinshipAtom(GenerationOffset.SAME, Gender.MALE, Lineage.UNKNOWN)),
    // SISTER - 对应 A15 和 A16
    SISTER("A15,A16", new KinshipAtom(GenerationOffset.SAME, Gender.FEMALE, Lineage.UNKNOWN)),

    // SON
    SON("A3", new KinshipAtom(GenerationOffset.DOWN, Gender.MALE, Lineage.UNKNOWN)),
    // DAUGHTER
    DAUGHTER("A4", new KinshipAtom(GenerationOffset.DOWN, Gender.FEMALE, Lineage.UNKNOWN)),

    // HUSBAND
    HUSBAND("A5", new KinshipAtom(GenerationOffset.SAME, Gender.MALE, Lineage.SPOUSE)),
    // WIFE
    WIFE("A6", new KinshipAtom(GenerationOffset.SAME, Gender.FEMALE, Lineage.SPOUSE));

    private final String codes; // 逗号分隔的多个编码
    public final KinshipAtom atom;

    RelationType(String codes, KinshipAtom atom) {
        this.codes = codes;
        this.atom = atom;
    }

    public static RelationType from(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return RelationType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            for (RelationType type : RelationType.values()) {
                if (containsCode(type.codes, value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No matching RelationType for value: " + value);
        }
    }

    /**
     * 检查codes中是否包含指定的value
     */
    private static boolean containsCode(String codes, String value) {
        if (codes == null || value == null) {
            return false;
        }
        String[] codeArray = codes.split(",");
        for (String code : codeArray) {
            if (code.trim().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public String getCodes() {
        return codes;
    }
}
