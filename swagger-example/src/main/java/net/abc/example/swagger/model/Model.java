package com.github.sunnus3.swagger.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangwei
 * @date: 19:53/2019-01-19
 */
@Data
@NoArgsConstructor
public class Model {

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "age")
    private int age;

    public Model(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
