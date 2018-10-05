package com.example.spring.routerfunctiondemo.handler;

import com.example.spring.routerfunctiondemo.exceptions.CheckException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @program: routerfunction-demo
 * @description: 异常处理器
 * @author: 01
 * @create: 2018-10-05 14:25
 **/
@Slf4j
@Order(-2) // 设置这个异常处理器的优先级，数字越小优先级越高
@Component
public class ExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        // 设置响应头
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        // 设置返回类型
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

        // 获取异常信息
        String errorMsg = toStr(ex);
        DataBuffer db = response.bufferFactory().wrap(errorMsg.getBytes());

        return response.writeWith(Mono.just(db));
    }

    /**
     * @param ex ex
     * @return error message
     */
    private String toStr(Throwable ex) {
        // 已知异常
        if (ex instanceof CheckException) {
            CheckException e = (CheckException) ex;
            return "参数错误: " + e.getFieldName() + ":" + e.getFieldValue();
        }
        // 未知异常，需要打印堆栈，方便定位问题
        else {
            log.error("", ex);
            return ex.toString();
        }
    }
}
