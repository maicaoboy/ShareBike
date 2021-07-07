package com.neu.pojo;

/**
 * 单车类
 */

public class Bike {
    //单车ID,数值范围10000000-99999999
    private int ID;                                 //单车ID
    private static int idRecord = 10000000;
    private int state;                              //单车状态,0-车辆正常并且空闲，1-使用中，2-维修状态，3-报废状态
    private UsingRecord record;                     //单车使用记录

    public static void setIdRecord(int idRecord) {
        Bike.idRecord = idRecord;
    }

    public static int getIdRecord() {
        return idRecord;
    }


    public Bike() {
        this.ID = idRecord;
        state = 0;
        idRecord++;
        record = null;
    }

    public Bike(int ID, int state) {
        this.ID = ID;
        this.state = state;
    }

    public int getID() {
        return ID;
    }

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

    //重写equals方法，以ID验证
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
