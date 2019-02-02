package com.github.sunnus3.example.spring;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangwei
 * @date: 15:11/2019-01-22
 */
@Data
@NoArgsConstructor
public class User {

    private Integer id;

    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
