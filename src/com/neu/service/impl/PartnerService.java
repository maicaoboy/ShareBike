package com.neu.service.impl;

import com.neu.dao.IBikeDao;
import com.neu.dao.IPartnerDao;
import com.neu.dao.impl.BikeDao;
import com.neu.dao.impl.PartnerDao;
import com.neu.pojo.Bike;
import com.neu.pojo.Partner;
import com.neu.service.IPartnerService;
import com.neu.utils.Util;
import com.neu.view.SystemUI;

/**
 * 合作方业务
 */

public class PartnerService implements IPartnerService {
    static IBikeDao bikeDao;
    static IPartnerDao partnerDao;
    private static PartnerService instance;

    public static PartnerService getInstance() {
        if(instance == null) {
            instance = new PartnerService();
        }
        return instance;
    }

    private PartnerService () {}

    static{
        bikeDao = (BikeDao) Util.getObject("BikeDao");
        partnerDao = (PartnerDao) Util.getObject("PartnerDao");
    }



    //合作方登录
    @Override
    public void login() {
        boolean log = false;
        String message[] = SystemUI.login();
        for(Partner partner : partnerDao.getPartners()) {
            if(partner.equals(new Partner(message[0],message[1]))) {
                partnerDao.setCurrentPartner(partner);
                log = true;
            }
        }
        SystemUI.loginReminder(log, message);

    }

    //合作方投放单车
    @Override
    public void releaseBike() {
        int total01 = 0;
        int total2 = 0;
        int add = 0;
        int result = 0; //0添加，1足够10车，不需投放，2要先修
        boolean addOrNor;
        for(Bike bike : bikeDao.getBikes()) {
            if(bike.getState() == 0 || bike.getState() == 1){
                total01 ++;
            }
            if(bike.getState() == 2) {
                total2 ++;

            }
        }
        if(total01 >= 10) {
            result = 0;
        }else{
            if(total2 != 0){
                result = 2;
            }else {
                result = 1;
                add = 10 - total01;
                for(int i = 0; i < add; i ++) {
                    Bike bike = new Bike();
                    bikeDao.getBikes().add(bike);
                    SystemUI.addBikeReminder(bike);
                }
             }
        }
        bikeDao.storeBike();
        SystemUI.releaseBikeReminder(result,add);
    }


    //合作方修理单车
    @Override
    public void fixBike() {
        int choose;
        Bike fix = null;
        boolean find = false;
        while(true) {
            if('n' == SystemUI.continueOrNot("要维修单车吗（输入y/n）选择")){
                break;
            }
            SystemUI.showFixs(bikeDao.getBikes());
            choose = SystemUI.getInt("请输入单车ID选择维修的单车");
            for(Bike bike : bikeDao.getBikes()) {
                if(bike.getID() == choose) {
                    fix = bike;
                    find = true;
                }
            }
            if(find) {
                if('n' == SystemUI.continueOrNot("该单车还可以维修吗吗（输入y/n）选择")){
                    fix.setState(4);
                }else {
                    fix.setState(0);
                }
                bikeDao.storeBike();
            }
            SystemUI.fixReminder(fix,choose);
        }
    }

    //登出
    @Override
    public void logout() {
        partnerDao.setCurrentPartner(null);
    }
}
