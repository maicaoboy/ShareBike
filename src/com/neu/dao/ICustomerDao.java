package com.neu.dao;

import com.neu.pojo.Customer;

import java.util.ArrayList;

/**
 * 用户操作接口
 */

public interface ICustomerDao {
    //添加一个用户
    boolean addCustomer(Customer customer);

//    int deleteCustomer(Customer customer);

    //登录验证函数
    boolean verify(Customer customer);

    ArrayList<Customer> getCustomers();

    //由输入的信息构造的只含用户名和密码的假用户取得真正用户对象
    Customer getCustomer(Customer customer);

    //设置当前登录用户
    void setCurrentCustomer(Customer customer);

    Customer getCurrentCustomer();

    //保存当前用户文件
    void storeCustomer();
}
