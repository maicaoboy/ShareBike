package com.neu.service;

public interface IBikeService {
    void register();
    void login();
    void useBike();
    void backBike();
    void fixBike();
    void deposit();
    void drawback();
    boolean available(int id);
}
