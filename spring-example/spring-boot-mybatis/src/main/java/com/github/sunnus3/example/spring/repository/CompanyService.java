package com.github.sunnus3.example.spring.repository;

import com.github.sunnus3.example.spring.dao.CompanyDao;
import com.github.sunnus3.example.spring.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * @author: zhangwei
 * @date: 10:31/2019-01-24
 */
@Repository
public class CompanyService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CompanyDao companyDao;

    @Transactional(rollbackFor = {Exception.class})
    public int update(Company company){
        companyDao.update(company);
        throw new NullPointerException("1");
    }

    @Transactional
    public List<Company> selectAll(){
        return companyDao.selectAll();
    }


    @Transactional
    public void test(){
        Connection connection = DataSourceUtils.getConnection(dataSource);
    }
}
