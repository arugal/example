package com.github.arugal.example.flink;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * @author zhangwei
 */
@Getter
public class ResourceAttribute {


	public ResourceAttribute(String name, String describe, boolean required) {
		this(name, describe, required, false, null);
	}

	public ResourceAttribute(String name, String describe, boolean required,
							 Function<ResourceAttribute, Map<String, String>> transformFunc, Function<ResourceAttribute, String> validateFunc) {
		this(name, describe, required, false, null, transformFunc, validateFunc);
	}

	public ResourceAttribute(String name, String describe, boolean required, boolean select, List<String> options) {
		this(name, describe, required, select, options, null, null);
	}

	public ResourceAttribute(String name, String describe, boolean required, boolean select, List<String> options,
							 Function<ResourceAttribute, Map<String, String>> transformFunc, Function<ResourceAttribute, String> validateFunc) {
		this.name = name;
		this.describe = describe;
		this.required = required;
		this.select = select;
		this.options = options;
		this.transformFunc = transformFunc;
		this.validateFunc = validateFunc;
	}

	/**
	 * 属性名称
	 */
	private final String name;

	/**
	 * 属性描述
	 */
	private final String describe;

	/**
	 * 参数值
	 */
	@Setter
	private String value;

	/**
	 * 是否必填
	 */
	private final boolean required;

	/**
	 * 是否下拉列表
	 */
	private final boolean select;

	/**
	 * 下拉菜单选项
	 */
	private final List<String> options;

	private Function<ResourceAttribute, Map<String, String>> transformFunc;

	public ResourceAttribute transformFunc(Function<ResourceAttribute, Map<String, String>> transformFunc) {
		requireNonNull(transformFunc);
		this.transformFunc = transformFunc;
		return this;
	}

	/**
	 * 转换为Flink参数
	 *
	 * @return
	 */
	public Map<String, String> toFlinkProperties() {
		requireNonNull(transformFunc, "please setting function of transform");
		return transformFunc.apply(this);
	}

	/**
	 * 参数校验函数,如果参数正常请返回null,如果异常请返回提示信息
	 */
	private Function<ResourceAttribute, String> validateFunc;

	public ResourceAttribute validateFunc(Function<ResourceAttribute, String> validateFunc) {
		requireNonNull(validateFunc);
		this.validateFunc = validateFunc;
		return this;
	}


	/**
	 * 参数校验
	 *
	 * @return
	 */
	public boolean validate() throws IllegalAttributeException {
		requireNonNull(validateFunc, "please setting function of test");
		String hint = validateFunc.apply(this);
		if (nonNull(hint)) {
			throw new IllegalAttributeException(this, hint);
		}
		return true;
	}

	@Override
	public String toString() {
		return "ResourceAttribute{" +
			"name='" + name + '\'' +
			", describe='" + describe + '\'' +
			", value='" + value + '\'' +
			", required=" + required +
			", select=" + select +
			'}';
	}
}
