package com.example.spring.routerfunctiondemo.handler;

import com.example.spring.routerfunctiondemo.domain.User;
import com.example.spring.routerfunctiondemo.repository.UserRepository;
import com.example.spring.routerfunctiondemo.utils.CheckUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.*;
import static org.springframework.http.MediaType.*;
/*
    ServerResponse 相当于 HttpServletResponse
    ServerRequest 相当于 HttpServletRequest
    每个方法的参数必须为ServerRequest，返回必须为ServerResponse
 */

/**
 * @program: routerfunction-demo
 * @description: 用户接口处理器，输入ServerRequest返回ServerResponse
 * @author: 01
 * @create: 2018-10-05 12:56
 **/
@Component
public class UserHandler {

    private final UserRepository userRepository;

    @Autowired
    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 获取所有用户
     *
     * @param request request
     * @return 所有用户数据
     */
    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON_UTF8)
                .body(userRepository.findAll(), User.class);
    }

    /**
     * 创建用户
     *
     * @param request request
     * @return 添加的用户数据
     */
    public Mono<ServerResponse> createUser(ServerRequest request) {
        // 获取用户提交的数据
        Mono<User> userMono = request.bodyToMono(User.class);

        return userMono.flatMap(newUser -> {
            // 检验用户名称
            CheckUtil.checkName(newUser.getName());

            return ok().contentType(APPLICATION_JSON_UTF8)
                    .body(userRepository.save(newUser), User.class);
        });
    }

    /**
     * 修改用户数据
     *
     * @param request request
     * @return 修改后的用户数据
     */
    public Mono<ServerResponse> updateUser(ServerRequest request) {
        // 获取用户提交的数据
        Mono<User> userMono = request.bodyToMono(User.class);
        // 从路径中获取用户id
        String id = request.pathVariable("id");

        return userMono.flatMap(updateUser -> {
            // 检验用户名称
            CheckUtil.checkName(updateUser.getName());

            return ok().contentType(APPLICATION_JSON_UTF8)
                    // 更新用户数据
                    .body(userRepository.findById(id).flatMap(oldUser -> {
                        // 将新的用户数据覆盖旧的用户数据
                        BeanUtils.copyProperties(updateUser, oldUser);
                        oldUser.setId(id);

                        return userRepository.save(oldUser);
                    }), User.class);
        });
    }

    /**
     * 根据id删除用户
     *
     * @param request request
     * @return 用户数据存在，删除成功返回200，用户不数据存在，删除失败返回404
     */
    public Mono<ServerResponse> deleteUserById(ServerRequest request) {
        // 从路径中获取用户id
        String id = request.pathVariable("id");

        return userRepository.findById(id).flatMap(user -> userRepository.delete(user).then(ServerResponse.ok().build()))
                .switchIfEmpty(notFound().build());
    }
}
