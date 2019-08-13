package com.github.arugal.example.spring;

import lombok.*;

/**
 * @author zhangwei
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements java.io.Serializable{

	private static final long serialVersionUID = 32384963180179804L;
	private Integer id;
	private String name;
	private Integer age;
}
