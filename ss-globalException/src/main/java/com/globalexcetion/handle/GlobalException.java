package com.globalexcetion.handle;

import com.study.example.entity.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        logger.error("全局异常捕获:{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 运行时异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<String> runtimeExceptionHandler(RuntimeException e) {
        logger.error("运行时异常捕获:{}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 空指针异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<String> nullPointerExceptionHandler(NullPointerException e) {
        logger.error("空指针异常捕获:{}", e.getMessage());
        return Result.error(e.getMessage());
    }
}
