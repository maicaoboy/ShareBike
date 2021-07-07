package com.neu.dao;

import com.neu.pojo.Bike;

import java.util.ArrayList;

/**
 * 单车数据操作接口
 */

public interface IBikeDao {
    //取得单车集合
    ArrayList<Bike> getBikes();
    //由ID查找单车并返回查找的单车
    Bike findBike(int id);
    //保存单车数据到Bikes.json中
    void storeBike();
}
