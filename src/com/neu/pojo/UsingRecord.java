package com.neu.pojo;

import java.util.Calendar;

//使用记录
public class UsingRecord {
    //使用单车用户ID
    int userID;

    int money;

    //被使用单车ID
    int bikeID;

    //使用开始时间
    Calendar beginTime;

    //使用结束时间
    Calendar endTime;

    public int getUserID() {
        return userID;
    }

    public UsingRecord(int userID, int bikeID, Calendar beginTime) {
        this.userID = userID;
        this.bikeID = bikeID;
        this.beginTime = beginTime;
        this.endTime = beginTime;
        money = 0;
    }

    public UsingRecord(int userID, int bikeID, Calendar beginTime, Calendar endTime) {

        this.userID = userID;
        this.bikeID = bikeID;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public UsingRecord() {}

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBikeID() {
        return bikeID;
    }

    public void setBikeID(int bikeID) {
        this.bikeID = bikeID;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Calendar beginTime) {
        this.beginTime = beginTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "{\"UsingRecord\":{"
                + "\"userID\":"
                + userID
                + ",\"bikeID\":"
                + bikeID
                + ",\"beginTime\":"
                + beginTime
                + ",\"endTime\":"
                + endTime
                + "}}";

    }
}
