package com.github.arugal.example.flink;

import lombok.Getter;

/**
 * @author zhangwei
 */
public class IllegalAttributeException extends RuntimeException{

	private static final long serialVersionUID = 1556987367587875365L;

	@Getter
	private ResourceAttribute attribute;

	public IllegalAttributeException(ResourceAttribute attribute, String hint) {
		super(hint);
		this.attribute = attribute;
	}
}
