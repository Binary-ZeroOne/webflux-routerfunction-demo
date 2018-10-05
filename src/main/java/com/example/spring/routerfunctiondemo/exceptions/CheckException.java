package com.example.spring.routerfunctiondemo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @program: webflux-demo
 * @description: 自定义参数校验异常
 * @author: 01
 * @create: 2018-10-05 11:27
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckException extends RuntimeException {

    /**
     * 检验不通过的字段名称
     */
    private String fieldName;

    /**
     * 检验不通过的字段的值
     */
    private String fieldValue;

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
