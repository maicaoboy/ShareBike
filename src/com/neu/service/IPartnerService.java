package com.neu.service;


/**
 * 合作方服务接口
 */
public interface IPartnerService {
    //登录
    void login();
    //投放单车业务
    void releaseBike();
    //修理单车
    void fixBike();
    //登出
    void logout();
}
