package com.neu.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.dao.ICustomerDao;
import com.neu.pojo.Customer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据操作对象
 */

public class CustomerDao implements ICustomerDao {
    private ArrayList<Customer> customers;
    private Customer currentCustomer;
    private static CustomerDao instance;


    private CustomerDao(){
        customers = new ArrayList<Customer>();
        currentCustomer  = null;

        //第一次运行加载单车文件
        try {
            File file = new File("Customers.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                customers = om.readValue(file,new TypeReference<List<Customer>>(){});
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //单例模式
    public static CustomerDao getInstance() {
        if(instance == null) {
            instance = new CustomerDao();
        }
        return instance;
    }


    //添加一个用户
    @Override
    public boolean addCustomer(Customer customer) {
        boolean result = true;
        for(Customer customer1 : customers) {
            if(customer1.equals(customer)) {
                result = false;
                break;
            }
        }
        if(result) {
            customers.add(customer);
        }
        return result;
    }

//    //删除一个用户
//    @Override
//    public int deleteCustomer(Customer customer) {
//        int result = 0;
//        Customer customer1 = null;
//        for(Customer customer2 : customers) {
//            if (customer.equals(customer2)){
//                customer1 = customer2;
//                break;
//            }
//        }
//        if(customer1 != null) {
//            if(customer1.getUsingBike() == null) {
//                result = 0;
//                customers.remove(customer1);
//            }else {
//                result = 1;
//            }
//        }else {
//            result = 2;
//        }
//        return result;
//    }

    //登录验证函数
    @Override
    public boolean verify(Customer customer) {
        boolean result = false;
        for(Customer customer1 : customers) {
            if(customer1.equals(customer)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    //由输入的信息构造的只含用户名和密码的假用户取得真正用户对象
    @Override
    public Customer getCustomer(Customer customer) {
        Customer customer1 = null;
        for(Customer customer2 : customers) {
            if(customer.equals(customer2)) {
                customer1 = customer2;
                break;
            }
        }
        return customer1;
    }

    //设置当前登录用户
    @Override
    public void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }

    //取得当前登录用户
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    //保存用户文件
    @Override
    public void storeCustomer() {
        try {
            File file = new File("Customers.json");
            ObjectMapper om = new ObjectMapper();
            om.writeValue(file,customers);;
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
