package com.github.sunnus3.example.spring.entity;

import lombok.Data;

/**
 * @author: zhangwei
 * @date: 10:54/2019-01-24
 */
@Data
public class Company {

    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private Double salary;
}
