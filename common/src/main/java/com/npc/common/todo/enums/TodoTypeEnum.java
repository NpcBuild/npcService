package com.npc.common.todo.enums;

/**
 * @program: npcService
 * @description 任务类型枚举
 * @author: feiyang
 * @create: 2025/06/15 21:01
 **/
public enum TodoTypeEnum {
    /**
     * 普通任务
     */
    Normal("1"),

    /**
     * 计数任务
     */
    Counting("2"),

    /**
     * 周期任务
     */
    Periodic("3");

    private final String value;

    TodoTypeEnum(String value) {
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
    public static TodoTypeEnum fromValue(String value) {
        for (TodoTypeEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
