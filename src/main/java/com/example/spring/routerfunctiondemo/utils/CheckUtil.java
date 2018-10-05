package com.example.spring.routerfunctiondemo.utils;


import com.example.spring.routerfunctiondemo.exceptions.CheckException;

import java.util.stream.Stream;

/**
 * @program: webflux-demo
 * @description: 校验工具类
 * @author: 01
 * @create: 2018-10-05 11:26
 **/
public class CheckUtil {

    private static final String[] INVALID_NAMES = {"admin", "管理员"};

    /**
     * 校验用户名称，不成功抛出校验异常
     *
     * @param value value
     */
    public static void checkName(String value) {
        Stream.of(INVALID_NAMES).filter(name -> name.equalsIgnoreCase(value))
                .findAny().ifPresent(name -> {
            throw new CheckException("name", value);
        });
    }
}
