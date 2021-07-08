package com.neu.view;

import com.neu.pojo.Bike;
import com.neu.pojo.Customer;
import com.neu.pojo.Partner;
import com.neu.service.IBikeService;
import com.neu.service.IManagerService;
import com.neu.service.IPartnerService;
import com.neu.service.impl.BikeService;
import com.neu.service.impl.ManagerService;
import com.neu.service.impl.PartnerService;
import com.neu.utils.Util;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SystemUI {
    static IBikeService bikeService;
    static IManagerService managerService;
    static IPartnerService partnerService;

    static{
        bikeService = (BikeService) Util.getObject("BikeService");
        managerService = (ManagerService) Util.getObject("ManagerService");
        partnerService = (PartnerService) Util.getObject("PartnerService");
    }

    public static void main(String[] args) {
        while(true) {
            int choose1 = 0;
            mainPanel();
            choose1 = getInt("普通用户请按1，管理人员请按2,退出请按0");
            switch(choose1) {
                case 0 -> System.exit(0);
                case 1 -> {
                    while(true) {
                        customerPanel();
                        int choose2 = 0;
                        choose2 = getInt("请选择");
                        switch(choose2) {
                            case 1 -> bikeService.register();
                            case 2 -> bikeService.login();
                            case 3 -> bikeService.useBike();
                            case 4 -> bikeService.backBike();
                            case 5 -> bikeService.fixBike();
                            case 6 -> bikeService.deposit();
                            case 7 -> bikeService.drawback();
                            default -> {}
                        }
                        if(choose2 == 0){
                            bikeService.logout();
                            break;
                        }
                    }
                }
                case 2 -> {
                    while(true){
                        managerPanel();
                        int choose3 = 0;
                        choose3 = getInt("请选择");
                        switch(choose3) {
                            case 1 ->  {
                                while(true) {
                                    int choose4 = 0;
                                    partnerPanel();
                                    choose4 = getInt("请选择");
                                    switch(choose4) {
                                        case 1 -> partnerService.login();
                                        case 2 -> partnerService.releaseBike();
                                        case 3 -> {
                                            partnerService.fixBike();
                                        }
                                    }
                                    if(choose4 == 0){
                                        partnerService.logout();
                                        break;
                                    }
                                    }
                            }
                            case 2 -> {
                                while(true){
                                    System.out.println("");
                                    int password = SystemUI.getInt("输入密码（0退出）");
                                    if(password == 0) {
                                        break;
                                    }else if(password == 12345678) {
                                        System.out.println("登陆成功");
                                        manager();
                                        while(true) {
                                            int choose5 = 0;
                                            choose5 = getInt("输入选择");
                                            switch(choose5) {
                                                case 1 -> managerService.addPartner();
                                                case 2 -> managerService.deletePartner();
                                                case 3 -> managerService.deleteCustomer();
                                            }
                                            if(choose5 == 0) {
                                                break;
                                            }
                                        }
                                    }else{
                                        continue;
                                    }
                                }

                            }
                            default -> {
                            }
                        }
                        if(choose3 == 0) {
                            break;
                        }
                    }
                }
            }

        }
    }

    private static void manager() {
        System.out.println("1.新增合作方     2.删除合作方    3.删除用户");
        System.out.println("0.返回");
    }

    private static void partnerPanel() {
        System.out.println("1.登录    2.投放单车    3.修理单车");
        System.out.println("0.退出");
    }

    static void mainPanel() {
        System.out.println(" ");
        System.out.println("*****欢迎使用共享单车*****");
        System.out.println(" ");
        System.out.println();
    }

    static void customerPanel(){
        System.out.println("1.注册    2.登录      3.扫码用车");
        System.out.println("4.还车    5.故障保修   6.预存单车使用金");
        System.out.println("7.退还单车使用金       0.返回"      );
    }

    static void managerPanel() {
        System.out.println("1.合作方       2.管理员       3.客服");
        System.out.println("0.返回");
    }

    public static String[] login() {
        String[] message = new String[2];
        System.out.println("---->  登录界面");
        message[0] = getString("1.请输入用户名");
        message[1] = getString("2.请输入密码");
        return message;
    }

    public static String[] addPartner() {
        String[] message = new String[2];
        System.out.println("---->  添加合作方界面");
        message[0] = getString("1.请输入用户名");
        message[1] = getString("2.请输入密码");
        return message;
    }

    public static String[] register() {
        String[] message = new String[5];
        Random random = new Random();
        int id;
        System.out.println("----> 欢迎注册共享单车");
        message[0] = getString("1.请输入用户名");
        while(true) {
            if(bikeService.available(id = random.nextInt(9000) + 1000)) {
                break;
            }
        }
        message[1] = String.valueOf(id);
        message[2] = getString("请输入密码");
        message[3] = getString("请输入身份证");
        message[4] = getString("请输入手机号");
        return message;
    }

    //参数为提示，返回值获得输入
    public static String getString(String reminder) {
        //保存输入的变量
        Scanner scan = new Scanner(System.in);
        System.out.print(reminder + ":");
        return scan.next();
    }

    //参数为提示，返回值获得输入
    public static int getInt(String reminder) {
        //保存输入的变量
        Scanner scan = new Scanner(System.in);
        System.out.print(reminder + ":");
        return scan.nextInt();
    }

     public static void registerReminder(Boolean result, Object[] message) {
        if(result) {
            System.out.println("用户 " + (String)message[0] + " 注册成功.");
        }else {
            System.out.println("注册失败，用户已注册，请勿重复注册");
        }
    }

    public static void loginReminder(boolean result, String[] message) {
        if(result) {
            System.out.println("用户" + message[0] + "登录成功");
        }else{
            System.out.println("登陆失败，请重新检查密码");
        }
    }

    public static void showBike(ArrayList<Bike> bikes) {
        int num = 0;
        for(int i = 0; i < bikes.size(); i++) {
            if(bikes.get(i).getState() == 0) {
                num ++;
            }
        }
        System.out.println("您附近有" + num + "辆单车可用：");
        for(Bike a : bikes) {
            if(a.getState() == 0) {
            System.out.println(a.getID());
            }
        }
    }

    public static void showABike(Bike bike, int j) {
        if(j == 0) {
            System.out.println("您附近可用的单车有：");
        }
        if(bike.getState() == 0 || bike.getState() == 1) {
            System.out.println("Bike：" + bike.getID());
        }
    }

    public static void useBikeReminder(int i, Customer currentCustomer, int choose) {
        switch(i) {
            case 0 -> System.out.println("用户 " +currentCustomer.getName() +  " 使用单车 " + choose + "成功");
            case 1 -> System.out.println("用户未登录，请登录后再使用");
            case 2 -> System.out.println("你所选的单车 " + choose + " 不可用");
            case 3 -> System.out.println("用户 " + currentCustomer.getName() + " 还有正在进行的订单，请还车后再进行操作");
        }
    }

    public static void backBikeReminder(int i, int hour, Customer user) {
        //0-还车成功，1-用户未登录，2-没有订单，3-余额不足
        switch(i) {
            case 0 -> System.out.println("还车成功,本次消费" + (hour + 1) +  "元,您的账户还剩" + user.getMoney() + "元");
            case 1 -> System.out.println("用户未登录，请登录后再使用");
            case 2 -> System.out.println("你没有进行中的订单");
            case 3 -> System.out.println("本次订单需支付" + (hour + 1) + " 元,您的账户余额" + user.getMoney() + "元，余额不足");
        }
    }

    public static void showDrawbackReminder(boolean log, Customer currentCustomer) {
        if(!log) {
            System.out.println("对不起，您还未登录。");
        }else {
            System.out.println("退款成功，总共" + currentCustomer.getMoney() + "元");
        }
    }

    public static void unLogin() {
        System.out.println("对不起，请登陆后操作");
    }

    public static void depositReminder(Customer currentCustomer, int money) {
        if(money >= 0) {
            System.out.println(currentCustomer.getName() + "已充值成功" + money + "元");
            System.out.println("您的余额还有" + currentCustomer.getMoney() + "元");
        }else{
            System.out.println("请勿输入负数");
        }
    }

    public static void addPartnerReminder(boolean result, Partner newOne) {
        if(result) {
            System.out.println("管理员" + newOne.getName() + "添加成功");
        }else {
            System.out.println("管理员已存在，请勿重复添加");
        }
    }

    public static void showPartner(Partner partner, int i) {
        if(i == 0) {
            System.out.println("---> 删除合作方");
        }
        System.out.print("合作方" + (i+1) + ":" + partner.getName());
    }

    public static void deletePartnerReminder(boolean result, Partner partner) {
        if(result) {
            System.out.println("成功删除合作方" + partner.getName());
        }else {
            System.out.println("请勿越界输入");
        }
    }

    public static void showCustomer(Customer customer1, int i) {
        if(i == 0) {
            System.out.println("可删除用户如下：");
        }
        if(customer1.getMoney() == 0 && customer1.getUsingBike() == null){
            System.out.println("用户" + (i + 1) + ":" + customer1.getName() + "    " + customer1.getID());
        }
    }

    public static void deleteCustomerReminder(boolean result, Customer customer) {
        if(result) {
            System.out.println("用户" + customer.getName() + "删除成功");
        }else{
            System.out.println("删除失败，请输入正确的ID");
        }
    }

    public static void showFixs(ArrayList<Bike> bikes) {
        System.out.println("可维修车辆如下：");
        int i = 1;
        for(Bike bike : bikes) {
            if(bike.getState() == 2) {
                System.out.println("车辆:" + bike.getID());
            }
        }
    }

    //是否继续的提示（参数）及输入（返回值）
    public static char continueOrNot(String reminder) {
        char get = 'n';
        Scanner scan = new Scanner(System.in);
        System.out.print(reminder + ":");
        get = scan.next().charAt(0);
        return get;
    }

    public static void fixReminder(Bike fix, int choose) {
        if(fix == null) {
            System.out.println("未找到单车" + choose);
        }else {
            if(fix.getState() == 0 || fix.getState() == 1) {
                System.out.println("单车" + choose + "维修成功");
            }else {
                System.out.println("单车" + choose + "维修失败，报废了");
            }
        }
    }

    public static void fixReminder1(Bike fix, int choose) {
        if(fix == null) {
            System.out.println("未找到单车" + choose);
        }else {
                System.out.println("单车" + choose + "报修成功");
        }
    }

    public static void addBikeReminder(Bike bike) {
        System.out.println("添加单车：" + bike.getID());
    }

    public static void releaseBikeReminder(int result, int add) {
        switch (result) {
            case 0 -> System.out.println("添加了" + add + "辆单车");
            case 1 -> System.out.println("已经达到投放密度，无需再投放单车");
            case 2 -> System.out.println("请先维修单车再投放");
        }
    }
}
