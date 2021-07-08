package com.neu.service.impl;

import com.neu.dao.ICustomerDao;
import com.neu.dao.IPartnerDao;
import com.neu.dao.impl.CustomerDao;
import com.neu.dao.impl.PartnerDao;
import com.neu.pojo.Customer;
import com.neu.pojo.Partner;
import com.neu.service.IManagerService;
import com.neu.utils.Util;
import com.neu.view.SystemUI;

/**
 * 管理员业务
 */

public class ManagerService implements IManagerService {
    static IPartnerDao partnerDao;
    static ICustomerDao customerDao;
    private static ManagerService instance;

    public static ManagerService getInstance() {
        if(instance == null) {
            instance = new ManagerService();
        }
        return instance;
    }

    private ManagerService() {
        partnerDao = (PartnerDao) Util.getObject("PartnerDao");
        customerDao = (CustomerDao) Util.getObject("CustomerDao");
    }


    //添加单车管理合作方
    @Override
    public void addPartner() {
        boolean result = true;
        String[] message = SystemUI.addPartner();
        Partner newOne = new Partner(message[0],message[1]);
        for(Partner partner : partnerDao.getPartners()) {
            if(partner.equals(newOne)) {
                result = false;
            }
        }
        if(result) {
            partnerDao.addPartner(newOne);
            partnerDao.store();
        }
        SystemUI.addPartnerReminder(result,newOne);
    }

    //删除单车管理合作方
    @Override
    public void deletePartner() {
        int i = 0;
        int delete = -1;
        boolean result = false;
        Partner partner = null;
        for(Partner partner1 : partnerDao.getPartners()) {
            SystemUI.showPartner(partner1,i++);
        }
        delete = SystemUI.getInt("请选择要删除的合作方");
        if(0 < delete && delete <= partnerDao.getPartners().size()) {
            partner = partnerDao.getPartners().get(delete - 1);
            partnerDao.getPartners().remove(delete - 1);
            partnerDao.store();
            result = true;
        }
        SystemUI.deletePartnerReminder(result,partner);
    }

    //删除用户
    @Override
    public void deleteCustomer() {
        int i = 0;
        int delete = 0;
        boolean result = false;
        Customer customer = null;
        for(Customer customer1: customerDao.getCustomers()) {
            SystemUI.showCustomer(customer1,i++);
        }
        delete = SystemUI.getInt("请输入要删除的用户的ID");
        for(Customer customer1 : customerDao.getCustomers()) {
            if (customer1.getID() == delete) {
                customerDao.getCustomers().remove(customer1);
                customerDao.storeCustomer();
                result = true;
                break;
            }
        }
        SystemUI.deleteCustomerReminder(result,customer);
    }
}
