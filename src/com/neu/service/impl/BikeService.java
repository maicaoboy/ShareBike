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
import java.util.Calendar;

/**
 * 共享单车业务
 */

public class BikeService implements IBikeService {
    private IBikeDao bikeDao;
    private ICustomerDao customerDao;
    private IUsingRecordDao usingRecordDao;
    private static BikeService instance;

    public static BikeService getInstance() {
        if(instance == null) {
            instance = new BikeService();
        }
        return instance;
    }

    private BikeService() {
        bikeDao = BikeDao.getInstance();
        customerDao = CustomerDao.getInstance();
        usingRecordDao = UsingRecordDao.getInstance();
    }


    //注册业务
    @Override
    public void register() {
        //获取用户输入信息，长度未5，1用户名，2用户ID，3密码，4身份证，5手机号
        String[] message = SystemUI.register();
        boolean result = customerDao.addCustomer(new Customer(message[0],Integer.parseInt(message[1]),message[2],message[3],message[4]));
        if(result){
            customerDao.storeCustomer();
        }
        SystemUI.registerReminder(result,message);
    }

    //登陆业务
    @Override
    public void login() {
        boolean result;
        //获取用户输入信息，长度未2，1用户名，2密码
        String[] message = SystemUI.login();
        result = customerDao.verify(new Customer(message[0],message[1]));
        //若登陆成功，则设置登录用户
        if(result) {
            customerDao.setCurrentCustomer(customerDao.getCustomer(new Customer(message[0],message[1])));
        }
        SystemUI.loginReminder(result,message);
    }

    //扫码用车
    @Override
    public void useBike() {
        int choose = 0;
        int i = 0;          //0-成功，1-用户未登录，2-单车不可用,3-用户还有正在进行的订单
        if(customerDao.getCurrentCustomer() == null) {
            i = 1;
        }else {
            //显示单车信息并让用户选择
            SystemUI.showBike(bikeDao.getBikes());
            choose = SystemUI.getInt("请扫码解锁单车（bushi）");
            Bike bike = bikeDao.findBike(choose);
            if(bike == null) {
                i = 2;
            }else if(customerDao.getCurrentCustomer().getUsingBike() != null){
                i = 3;
            }else {
                //判断车辆可用并且当前用户五正在进行的订单后进入用车
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

    //归还单车
    @Override
    public void backBike() {
        Customer user = null;
        Calendar backTime;
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
                //判断用户已登陆，有用车，余额充足允许还车
                hour = (int)(customerDao.getCurrentCustomer().getUsingBike().getRecord().getBeginTime().getTimeInMillis()
                        - backTime.getTimeInMillis())/(1000*3600);
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
        SystemUI.backBikeReminder(i,hour,user);
    }


    //报修单车
    @Override
    public void fixBike() {
        Bike fixBike = null;
        if(customerDao.getCurrentCustomer() == null) {
            SystemUI.unLogin();
            return;
        }
        for(int j = 0; j < bikeDao.getBikes().size(); j ++) {
            SystemUI.showABike(bikeDao.getBikes().get(j), j);
        }
        int fix = SystemUI.getInt("请输入要报修的单车ID");
        for(Bike bike : bikeDao.getBikes()) {
            if(bike.getID() == fix) {
                fixBike = bike;
                bike.setState(2);
                bikeDao.storeBike();
                break;
            }
        }
        SystemUI.fixReminder1(fixBike,fix);
    }

    //存钱
    @Override
    public void deposit() {
        if(customerDao.getCurrentCustomer() == null) {
            SystemUI.unLogin();
            return;
        }else {
            int money;
            money = SystemUI.getInt("请输入要充值的金额（整数）");
            if(money >= 0) {
                customerDao.getCurrentCustomer().setMoney(customerDao.getCurrentCustomer().getMoney() + money);
                customerDao.storeCustomer();
            }
            SystemUI.depositReminder(customerDao.getCurrentCustomer(),money);
        }

    }

    //退款
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

    //验证用户ID是否可用
    @Override
    public boolean available(int id) {
        for(Customer customer : customerDao.getCustomers()) {
            if(customer.getID() == id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void logout() {
        customerDao.setCurrentCustomer(null);
    }
}
