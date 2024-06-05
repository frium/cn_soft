package com.fyy.common;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @date 2024-05-21 20:37:35
 * @description
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private HttpServletResponse response;

    @ExceptionHandler
    public R<?> myExceptionHandler(MyException e) {
        log.info("业务异常信息：{}", e.getMessage());
        if (e.getStatusCodeEnum() != null) {
            response.setStatus(e.getHttpStatusCode());
            return R.error(e.getStatusCodeEnum());
        }
        response.setStatus(e.getHttpStatusCode());
        return R.error(e.getMessage());
    }


    @ExceptionHandler
    public R<?> formatExceptionHandler(HttpMediaTypeNotSupportedException e) {
        log.error("参数格式错误, {}", e.getMessage());
        response.setStatus(StatusCodeEnum.VALUE_ERROR.getHttpStatusCode());
        return R .error(StatusCodeEnum.VALUE_ERROR);
    }
    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public R<String> exceptionHandler(NullPointerException e) {
        log.error("发生空指针异常！ msg: -> ", e);
        return R.error("发生空指针异常!");
    }

    /**
     * 服务器异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> exception(Exception e) {
        log.error("服务器异常！ msg: -> ", e);
        return R.error("服务器异常!");
    }

}
