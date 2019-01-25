package com.github.sunnus3.example.spring.dao;

import com.github.sunnus3.example.spring.entity.Company;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: zhangwei
 * @date: 10:56/2019-01-24
 */
@Mapper
public interface CompanyDao {

    List<Company> selectAll();

    int add(Company company);

    int update(Company company);

    int delete(Company company);
}
