package com.npc.exception;

/**
 * @author NPC
 * @description 登录超时异常
 * @create 2023/8/31 22:15
 */
public class YFLoginTimeoutException extends RuntimeException {
    public YFLoginTimeoutException(String msg) {
        super( msg );
    }
}
