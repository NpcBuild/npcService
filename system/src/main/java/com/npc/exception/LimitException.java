package com.npc.exception;

/**
 * @author NPC
 * @description 限流自定义异常
 * @create 2023/4/15 20:58
 */
public class LimitException extends RuntimeException{
    public LimitException(String msg) {
        super( msg );
    }
}
