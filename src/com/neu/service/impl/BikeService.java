package com.neu.service.impl;

import com.neu.dao.IBikeDao;
import com.neu.dao.ICustomerDao;
import com.neu.dao.IUsingRecordDao;
import com.neu.dao.impl.BikeDao;
import com.neu.dao.impl.CustomerDao;
import com.neu.dao.impl.UsingRecordDao;
import com.neu.pojo.Bike;
import com.neu.pojo.Customer;
import com.neu.pojo.UsingRecord;
import com.neu.service.IBikeService;
import com.neu.view.SystemUI;

import java.awt.*;
import java.util.Calendar;

public class BikeService implements IBikeService {
    private IBikeDao bikeDao;
    private ICustomerDao customerDao;
    private IUsingRecordDao usingRecordDao;

    public BikeService() {
        bikeDao = BikeDao.getInstance();
        customerDao = CustomerDao.getInstance();
        usingRecordDao = UsingRecordDao.getInstance();
    }


    @Override
    public void register() {
        String[] message = SystemUI.register();
        boolean result = customerDao.addCustomer(new Customer(message[0],Integer.parseInt(message[1]),message[2],message[3],message[4]));
        if(result){
            customerDao.storeCustomer();
        }
        SystemUI.registerReminder(result,message);
    }

    @Override
    public void login() {
        boolean result = false;
        String[] message = SystemUI.login();
        result = customerDao.verify(new Customer(message[0],message[1]));
        if(result) {
            customerDao.setCurrentCustomer(customerDao.getCustomer(new Customer(message[0],message[1])));
        }
        SystemUI.loginReminder(result,message);
    }

    @Override
    public void useBike() {
        int choose = 0;
        int i = 0;          //0-成功，1-用户未登录，2-单车不可用,3-用户还有正在进行的订单
        if(customerDao.getCurrentCustomer() == null) {
            i = 1;
        }else {
            SystemUI.showBike(bikeDao.getBikes());
            choose = SystemUI.getInt("请扫码解锁单车（bushi）");
            Bike bike = bikeDao.findBike(choose);
            if(bike == null) {
                i = 2;
            }else if(customerDao.getCurrentCustomer().getUsingBike() != null){
                i = 3;
            }else {
                customerDao.getCurrentCustomer().setUsingBike(bikeDao.findBike(choose));
                bike.setRecord(new UsingRecord(customerDao.getCurrentCustomer().getID(),
                        choose,Calendar.getInstance()));
                bike.setState(1);
                customerDao.storeCustomer();
                bikeDao.storeBike();
            }
        }
        SystemUI.useBikeReminder(i,customerDao.getCurrentCustomer(),choose);
    }

    @Override
    public void backBike() {
        Customer user = null;
        Calendar backTime = null;
        int choose = 0;
        int hour = 0;
        int i = 0;          //0-还车成功，1-用户未登录，2-没有订单，3-余额不足
        if(customerDao.getCurrentCustomer() == null) {
            i = 1;
        }else {
            user = customerDao.getCurrentCustomer();
            backTime = Calendar.getInstance();
            if(customerDao.getCurrentCustomer().getUsingBike() == null) {
                i = 2;
            }else if(customerDao.getCurrentCustomer().getMoney() < 1 || customerDao.getCurrentCustomer().getMoney() < hour){
                i = 3;
            }else {
                hour = (int)(customerDao.getCurrentCustomer().getUsingBike().getRecord().getBeginTime().getTimeInMillis()
                        - backTime.getTimeInMillis())/(1000*3600);
                if(hour == 0) {
                    customerDao.getCurrentCustomer().setMoney(customerDao.getCurrentCustomer().getMoney() - 1);
                }else{
                    customerDao.getCurrentCustomer().setMoney(customerDao.getCurrentCustomer().getMoney() - 1 - hour);
                    customerDao.getCurrentCustomer().getUsingBike().getRecord().setEndTime(backTime);
                    usingRecordDao.addUsingRecord(customerDao.getCurrentCustomer().getUsingBike().getRecord());
                    usingRecordDao.storeUsingRecord();
                    customerDao.getCurrentCustomer().getUsingBike().setState(0);
                    customerDao.getCurrentCustomer().getUsingBike().setRecord(null);
                    bikeDao.storeBike();
                    customerDao.getCurrentCustomer().setUsingBike(null);
                    customerDao.storeCustomer();
                }
            }
        }
        SystemUI.backBikeReminder(i,hour,user);
    }

    @Override
    public void fixBike() {
        if(customerDao.getCurrentCustomer() == null) {
            SystemUI.unLogin();
            return;
        }
        for(int j = 0; j < bikeDao.getBikes().size(); j ++) {
            SystemUI.showABike(bikeDao.getBikes().get(j), j);
        }
    }

    @Override
    public void deposit() {
        if(customerDao.getCurrentCustomer() == null) {
            SystemUI.unLogin();
            return;
        }else {
            int money = 0;
            money = SystemUI.getInt("请输入要充值的金额（整数）");
            if(money >= 0) {
                customerDao.getCurrentCustomer().setMoney(customerDao.getCurrentCustomer().getMoney() + money);
                customerDao.storeCustomer();
            }
            SystemUI.depositReminder(customerDao.getCurrentCustomer(),money);
        }

    }

    @Override
    public void drawback() {
        boolean log = true;
        if(customerDao.getCurrentCustomer() == null) {
            log = false;
        }
        SystemUI.showDrawbackReminder(log,customerDao.getCurrentCustomer());
        if(log) {
            customerDao.getCurrentCustomer().setMoney(0);
            customerDao.storeCustomer();
        }
    }

    @Override
    public boolean available(int id) {
        for(Customer customer : customerDao.getCustomers()) {
            if(customer.getID() == id) {
                return false;
            }
        }
        return true;
    }
}
