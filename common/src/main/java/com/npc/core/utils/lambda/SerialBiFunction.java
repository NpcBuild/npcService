package com.npc.core.utils.lambda;

import java.io.Serializable;

/**
 * @program: npcService
 * @description 支持序列化的双参数函数接口
 * 规定传参和返回值的格式
 * @author: feiyang
 * @create: 2025/12/04 20:38
 **/
public interface SerialBiFunction<T, U, R> extends Serializable {
    // 方法格式：传入T（Service实例）和U（参数），返回R（结果）
    R apply(T t, U u);
}
