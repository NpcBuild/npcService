package com.npc.common.modular.serverErrorLog.exceptionHandler;

import com.npc.common.modular.serverErrorLog.entity.ServerErrorLog;
import com.npc.common.modular.serverErrorLog.mapper.ServerErrorLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//import jakarta.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
/**
 * @program: npcService
 * @description 监听controller异常
 * @author: feiyang
 * @create: 2025/10/25 13:35
 **/

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @Autowired
    private ServerErrorLogMapper serverErrorLogMapper;

    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception ex, HttpServletRequest request) {

        ServerErrorLog log = new ServerErrorLog();
        log.setErrorLevel("ERROR");
        log.setServiceName("NPC"); // 可根据模块动态设置
        log.setRequestUri(request.getRequestURI());
        log.setErrorMessage(ex.getMessage());

        // 获取堆栈信息
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        log.setStackTrace(sw.toString());

        // 可根据业务获取用户ID或其他上下文
        log.setUserId(1L);
        log.setExtraInfo("{\"params\":\"" + request.getQueryString() + "\"}");

        serverErrorLogMapper.insert(log);

        // 可根据需要返回自定义响应
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器错误");
    }
}