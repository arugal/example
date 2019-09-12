package com.github.arugal.example.flink;

import java.util.List;

/**
 * @author zhangwei
 */
public interface ResourceManager {

	//   Query Method
	List<SourceResource> getAllSourceResource();

	List<SourceResource> getSourceResource(String name);

	SourceResource getSourceResource(String name, String version);

	List<SinkResource> getAllSinkResource();

	List<SinkResource> getSinkResource(String name);

	SinkResource getSinkResource(String name, String version);


	// Update Method
	boolean addSourceResource(SourceResource resource);

	boolean addSourceResource(SourceResource resource, boolean replace);

	boolean addSinkResource(SinkResource resource);

	boolean addSinkResource(SinkResource resource, boolean replace);


	// Delete Method
	boolean delSourceResource(String name, String version);

	boolean delSourceResource(SourceResource resource);

	boolean delSinkResource(String name, String version);

	boolean delSinkResource(SinkResource resource);

}
