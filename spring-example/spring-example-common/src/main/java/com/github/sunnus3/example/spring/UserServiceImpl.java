package com.github.sunnus3.example.spring;

/**
 * @author: zhangwei
 * @date: 15:11/2019-01-22
 */
public class UserServiceImpl implements UserService{

    @Override
    public int save(User user) {
        System.out.println("execute save method!");
        print();
        return 1;
    }


    public void print(){
        System.out.println("print-method");
    }
}
