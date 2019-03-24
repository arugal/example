package com.github.arugal.example.es.dao;

import com.github.arugal.example.es.entity.Repositorie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 20:57/2019-01-04
 */
@Mapper
public interface RepositorieDao {

    List<Repositorie> getAllRepositorie(@Param("startRow") Integer startRow, @Param("endRow") Integer endRow);

}
