package com.npc.exception;

/**
 * @author NPC
 * @description 自定义异常
 * 把原始异常信息隐藏起来，仅向上提供必要的异常提示信息的处理方式，可以保证底层异常不会扩散到表现层，可以避免向上暴露太多的实现细节，这完全符合面向对象的封装原则。
 * @create 2023/4/14 21:46
 */
public class YFException extends RuntimeException {
    /**
     * 无参数的构造器
     */
    public YFException(){}

    /**
     * 带一个字符串参数的构造器
     * @param message 该异常对象的描述信息（也就是异常对象的getMessage()方法的返回值）
     */
    public YFException(String message){
        super(message);
    }


    /**
     * 创建一个可以接收Throwable参数的构造器
     * @param throwable
     */
    public YFException(Throwable throwable){
        super(throwable);
    }
}
