package com.npc.core.utils.lambda;

import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: npcService
 * @description 统一执行方法、打日志、处理异常的 “打工人”
 * @author: feiyang
 * @create: 2025/12/04 20:57
 **/
@Slf4j
@Setter
public class ServiceExecutor<T,U,R> {
    // 要执行的Lambda方法
    private SerialBiFunction<T, U, R> serviceFn;
    // 方法参数
    private U param;
    // Service信息
    private ServiceManager.LambdaMeta<T> lambdaMeta;

    // 执行方法的核心逻辑
    public ServerResponseVO<R> callService() {
        // 记录开始时间，方便算耗时
        long startTime = System.currentTimeMillis();
        String serviceName = lambdaMeta.getClazz().getSimpleName();  // 比如UserService
        String methodName = lambdaMeta.getServiceName();             // 比如queryUser
        log.info("开始调用：{}的{}方法，参数：{}", serviceName, methodName, param);
        try {
            // 真正执行方法：用Service实例调用Lambda方法
            R result = serviceFn.apply(lambdaMeta.getInst(), param);
            // 算耗时，打成功日志
            long costTime = System.currentTimeMillis() - startTime;
            log.info("调用成功：{}的{}方法，耗时{}ms，结果：{}",
                                        serviceName, methodName, costTime, result);
            // 返回成功结果
            return ServerResponseVO.success(result);
        } catch (Exception e) {
            // 出错了就打错误日志，返回失败结果
            long costTime = System.currentTimeMillis() - startTime;
            log.error("调用失败：{}的{}方法，耗时{}ms",
                                        serviceName, methodName, costTime, e);
            return ServerResponseVO.error(ServerResponseEnum.INTERNAL_SERVER_ERROR.getCode(),"调用" + serviceName + "的" + methodName + "方法失败：" + e.getMessage());
        }
    }
}
