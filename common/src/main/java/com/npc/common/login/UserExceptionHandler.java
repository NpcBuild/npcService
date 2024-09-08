package com.npc.common.login;

import com.npc.core.ServerResponseEnum;
import com.npc.core.ServerResponseVO;
import com.npc.exception.LimitException;
import com.npc.exception.YFLoginTimeoutException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * 用户身份认证失败时,统一异常处理
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    // @ResponseBody
    public ServerResponseVO UnAuthorizedExceptionHandler(UnauthorizedException e) {
        return ServerResponseVO.error(ServerResponseEnum.UNAUTHORIZED);
    }

    /**
     * try-catch
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // @ResponseBody
    public ServerResponseVO handleException(Exception e) {
        e.printStackTrace();
        if (e instanceof ArithmeticException) {
            return ServerResponseVO.error(ServerResponseEnum.DATA_EXCEPTION);
        }
        if (e instanceof LimitException) {
            return ServerResponseVO.error(ServerResponseEnum.SYSTEM_BUSY_ERROR);
        }
        if (e instanceof YFLoginTimeoutException) {
            return ServerResponseVO.error(ServerResponseEnum.TOKEN_INVALID);
        }
        if (e instanceof Exception) {
//            return ServerResponseVO.error(ServerResponseEnum.INTERNAL_SERVER_ERROR);
            return ServerResponseVO.error(ServerResponseEnum.INTERNAL_SERVER_ERROR.getCode(),e.getMessage());
        }
        return null;
    }
}