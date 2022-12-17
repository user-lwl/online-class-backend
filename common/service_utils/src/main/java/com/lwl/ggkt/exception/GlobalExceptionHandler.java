package com.lwl.ggkt.exception;

import com.lwl.ggkt.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常处理
     * @param e 异常
     * @return result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Object> error(Exception e){
        e.printStackTrace();
        return Result.fail().message("执行了全局异常处理");
    }

    /**
     * 特定异常处理
     * @param e 异常
     * @return result
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result<Object> error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理");
    }

    /**
     * 自定义异常处理
     * @param e 异常
     * @return result
     */
    @ExceptionHandler(GgktException.class)
    @ResponseBody
    public Result<Object> error(GgktException e){
        e.printStackTrace();
        return Result.fail().message(e.getMsg()).code(e.getCode());
    }
}