package com.neu.service.impl;

import com.neu.dao.IBikeDao;
import com.neu.dao.impl.BikeDao;
import com.neu.pojo.Bike;
import com.neu.service.IPartnerService;
import com.neu.view.SystemUI;

public class PartnerService implements IPartnerService {
    static IBikeDao bikeDao;

    static{
        bikeDao = BikeDao.getInstance();
    }


    @Override
    public void releaseBike() {
        int total = 0;
        for(Bike bike : bikeDao.getBikes()) {
            if(bike.getState() == 0 )
        }
    }

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
}
