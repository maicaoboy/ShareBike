package com.neu.service;

/**
 * 单车业务接口
 */

public interface IBikeService {
    //注册
    void register();
    //登录
    void login();
    //使用单车
    void useBike();
    //归还单车
    void backBike();
    //报修单车
    void fixBike();
    //存款
    void deposit();
    //退款
    void drawback();
    boolean available(int id);
    //登出
    void logout();
}
