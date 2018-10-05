package com.example.spring.routerfunctiondemo.repository;

import com.example.spring.routerfunctiondemo.domain.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @program: webflux-demo
 * @description: user dao层接口
 * @author: 01
 * @create: 2018-10-04 20:27
 **/

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    /**
     * 根据年龄段查找用户数据
     *
     * @param start start
     * @param end   end
     * @return 用户数据
     */
    Flux<User> findByAgeBetween(int start, int end);

    /**
     * 自定义MongoDB查询条件，查询20-45岁的用户数据
     *
     * @return 用户数据
     */
    @Query("{'age':{'$gte':20,'$lte':45}}")
    Flux<User> oldUser();
}
