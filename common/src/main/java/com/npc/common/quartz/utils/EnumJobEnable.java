package com.npc.common.quartz.utils;

/**
 * @author NPC
 * @description 任务状态枚举类
 * @create 2023/9/7 22:06
 */
public enum EnumJobEnable {
    START("2", "开启"),
    STOP("0", "关闭");

    private String code;

    private String msg;

    EnumJobEnable(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }
}
