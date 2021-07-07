package com.neu.dao;

import com.neu.pojo.Bike;

import java.util.ArrayList;

public interface IBikeDao {
    ArrayList<Bike> getBikes();
    Bike findBike(int id);
    void storeBike();
}
