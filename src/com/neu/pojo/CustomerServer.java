package com.neu.pojo;

import java.util.Objects;

public class CustomerServer {
    private String name;                //客服名称

    private int workID;                 //客服工号

    private String password;             //客服密码

    public CustomerServer(String name, int workID, String password) {
        this.name = name;
        this.workID = workID;
        this.password = password;
    }

    public CustomerServer() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkID() {
        return workID;
    }

    public void setWorkID(int workID) {
        this.workID = workID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        CustomerServer that = (CustomerServer) o;
        return Objects.equals(name, that.name) && Objects.equals(password, that.password);
    }

    @Override
    public String toString() {
        return "{\"CustomerServer\":{"
                + "\"name\":\""
                + name + '\"'
                + ",\"workID\":"
                + workID
                + ",\"password\":\""
                + password + '\"'
                + "}}";

    }
}
