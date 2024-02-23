package com.npc.core.utils;

/**
 * @author NPC
 * @description 数学类工具
 * @create 2023/4/8 18:09
 */
public class MathUtils {

    /**
     * 只适用于Double类型的值可以精确地表示为Long类型的值的情况
     * @param d
     * @param l
     * @return
     */
    private static boolean doubleEqualsLong(double d, long l) {
        return Double.valueOf(d).longValue() == l;
    }
}
