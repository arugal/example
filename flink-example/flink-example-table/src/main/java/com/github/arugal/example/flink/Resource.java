package com.github.arugal.example.flink;

import java.util.List;

/**
 * @author zhangwei
 */
public interface Resource {

	String getName();

	String getVersion();

	String getDescribe();

	List<ResourceAttribute> supportedAttribute();


}
