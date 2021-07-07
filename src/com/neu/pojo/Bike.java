package com.neu.pojo;

import com.neu.dao.impl.UsingRecordDao;

import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class Bike {
    //单车ID,数值范围10000000-99999999
    private int ID;
    private static int idRecord = 10000000;

    //单车状态,0-车辆正常并且空闲，1-使用中，2-维修状态，3-报废状态
    private int state;
    private UsingRecord record;

    public static void setIdRecord(int idRecord) {
        Bike.idRecord = idRecord;
    }

    public static int getIdRecord() {
        return idRecord;
    }



    public int getID() {
        return ID;
    }

    public Bike(int id) {
        this.ID = id;
        state = 0;
        idRecord++;
        record = null;
    }

    public Bike() { }

    /**
     * 构造函数
     */
    public Bike(int ID, int state) {
        this.ID = ID;
        this.state = state;
    }

    /**
     * setter和getter
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setRecord(UsingRecord record) {
        this.record = record;
    }

    public UsingRecord getRecord() {
        return record;
    }

    @Override
    public boolean equals(Object o) {
        Bike bike = (Bike) o;
        return ID == bike.ID;
    }

    @Override
    public String toString() {
        return "{\"Bike\":{"
                + "\"ID\":"
                + ID
                + ",\"state\":"
                + state
                + ",\"record\":"
                + record
                + "}}";

    }
}
