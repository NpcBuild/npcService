package com.npc.common.todo.enums;

/**
 * @program: npcService
 * @description 任务状态枚举
 * @author: feiyang
 * @create: 2025/06/15 21:01
 **/
public enum TodoEnum {
    /**
     * 未开始
     */
    PENDING("0"),

    /**
     * 进行中
     */
    ACTIVE("1"),

    /**
     * 已暂停
     */
    PAUSED("11"),

    /**
     * 已完成
     */
    COMPLETED("99"),

    /**
     * 已取消
     */
    CANCELED("-1"),

    /**
     * 已延期
     */
    DEFERRED("-9");

    private final String value;

    TodoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * 根据值获取对应的枚举
     *
     * @param value 枚举值
     * @return 对应的枚举对象
     */
    public static TodoEnum fromValue(String value) {
        for (TodoEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
