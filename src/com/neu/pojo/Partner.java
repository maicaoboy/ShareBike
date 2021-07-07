package com.neu.pojo;

import java.util.Objects;

/**
 * 合作方
 */
public class Partner {
    private String name;            //合作方名字

    private String password;        //合作方密码


    public Partner(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        Partner partner = (Partner) o;
        return Objects.equals(name, partner.name) && Objects.equals(password, partner.password);
    }

    public Partner() {}

    @Override
    public String toString() {
        return "{\"Partner\":{"
                + "\"name\":\""
                + name + '\"'
                + ",\"password\":\""
                + password + '\"'
                + "}}";

    }
}
