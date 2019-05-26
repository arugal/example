package com.github.arugal.example.druid;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhangwei
 * @date: 16:54/2019-03-29
 */
@Mapper
public interface SelectOne {

    @Select("select sleep(1)")
    Long select1s();

    @Select("select sleep(2)")
    Long select2s();

    @Select("select sleep(3)")
    Long select3s();

    @Select("select sleep(4)")
    Long select4s();

    @Select("select sleep(5)")
    Long select5s();


    @Transactional(propagation = Propagation.SUPPORTS)
    @Select("select5s sleep(0.1)")
    Long selectTransactional();
}
