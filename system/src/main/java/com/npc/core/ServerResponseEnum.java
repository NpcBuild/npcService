package com.npc.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统返回状态枚举与包装函数
 * 简易Shiro
 */
@AllArgsConstructor
@Getter
public enum ServerResponseEnum {
    SUCCESS(200, "成功"),
    ERROR(10, "失败"),

    ACCOUNT_NOT_EXIST(11, "账号不存在"),
    DUPLICATE_ACCOUNT(12, "账号重复"),
    ACCOUNT_IS_DISABLED(13, "账号被禁用"),
    ACCOUNT_IS_LOCKED(14, "账号被锁定,请稍后再试"),
    NOT_LOGIN_IN(15, "账号未登录"),
    UNAUTHORIZED(16, "没有权限"),
    INCORRECT_CREDENTIALS(17, "账号或密码错误"),

    DATA_EXCEPTION(25, "数据异常"),
    SAVE_FAILED(30, "保存失败"),
    UPDATE_FAILED(31, "更新失败"),
    DELETE_FAILED(32, "删除失败"),
    TASK_CRON_ERROR(40, "表达式错误"),
    TASK_CRON_DOUBLE(41, "定时任务已存在"),
    TASK_NOT_EXITES(42, "定时任务不存在"),
    TASK_EXCEPTION(43, "定时任务异常"),
    TOKEN_INVALID(401, "登录失效"),
    INTERNAL_SERVER_ERROR(500, "服务器内部异常"),
    SYSTEM_BUSY_ERROR(501, "系统繁忙，请稍后再试！");

    Integer code;
    String message;
}
