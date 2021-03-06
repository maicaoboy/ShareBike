package com.neu.pojo;

import java.util.Calendar;
import java.util.Objects;

public class Customer {
    public Customer(String name, int ID, String password, String identity, String phoneNumber) {
        this.name = name;
        this.ID = ID;
        this.password = password;
        this.identity = identity;
        this.phoneNumber = phoneNumber;
        this.money = 0;
        this.usingBike = null;
        this.registerTime = Calendar.getInstance();
        lendTime = null;
    }

    public Customer(String name,String password){
        this.name = name;
        this.password = password;
    }

    public Customer(){}

    private String name;                     //用户名

    private int ID;                          //用户ID

    private String password;                //密码

    private double money;                   //余额

    private Bike usingBike;                 //正在使用的单车

    private String identity;                //身份证

    private String phoneNumber;             //手机号

    private Calendar registerTime;          //注册时间

    private Calendar lendTime;               //单车借出时间


    public void setLendTime(Calendar lendTime) {
        this.lendTime = lendTime;
    }

    public Calendar getLendTime() {
        return lendTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Bike getUsingBike() {
        return usingBike;
    }

    public void setUsingBike(Bike usingBike) {
        this.usingBike = usingBike;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Calendar getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Calendar registerTime) {
        this.registerTime = registerTime;
    }

    //以名字和密码验证用户
    @Override
    public boolean equals(Object o) {
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(password, customer.password);
    }

    @Override
    public String toString() {
        return "{\"Customer\":{"
                + "\"name\":\""
                + name + '\"'
                + ",\"ID\":"
                + ID
                + ",\"password\":\""
                + password + '\"'
                + ",\"money\":"
                + money
                + ",\"usingBike\":"
                + usingBike
                + ",\"identity\":\""
                + identity + '\"'
                + ",\"phoneNumber\":\""
                + phoneNumber + '\"'
                + ",\"registerTime\":"
                + registerTime
                + ",\"lendTime\":"
                + lendTime
                + "}}";

    }
}
