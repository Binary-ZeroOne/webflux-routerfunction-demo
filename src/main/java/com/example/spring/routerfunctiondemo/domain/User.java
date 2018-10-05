package com.example.spring.routerfunctiondemo.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

/**
 * @program: webflux-demo
 * @description: User实体类
 * @author: 01
 * @create: 2018-10-04 20:25
 **/

@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;

    @NotBlank(message = "用户名称不可以为空")
    private String name;

    @Range(min = 10, max = 100, message = "用户年龄需在10-100岁之间")
    private int age;
}
