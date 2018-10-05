package com.example.spring.routerfunctiondemo.routers;

import com.example.spring.routerfunctiondemo.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

/**
 * @program: routerfunction-demo
 * @description: 统一的路由配置，将url和处理器关联起来
 * @author: 01
 * @create: 2018-10-05 13:58
 **/

@Configuration
public class AllRouters {

    @Bean
    public RouterFunction<ServerResponse> userRouter(UserHandler handler) {
        return nest(
                // 请求路径前缀，相当于我们 controller 类上面写的@RequestMapping("/user")
                path("/user"),
                // 获取所有用户接口，相当于我们在controller方法里写的@GetMapping("/get_all")
                route(GET("/get_all"), handler::getAllUser)
                        // 添加用户接口
                        .andRoute(POST("/save").and(accept(APPLICATION_JSON_UTF8)), handler::createUser)
                        // 更新用户接口
                        .andRoute(PUT("/update/{id}").and(accept(APPLICATION_JSON_UTF8)), handler::updateUser)
                        // 删除用户接口
                        .andRoute(DELETE("/del/{id}"), handler::deleteUserById)
        );
    }
}
