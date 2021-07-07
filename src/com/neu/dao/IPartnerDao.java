package com.neu.dao;

import com.neu.pojo.Partner;

import java.util.ArrayList;

public interface IPartnerDao {
    //添加合作方
    boolean addPartner(Partner partner);

    //获得合作方集合
    ArrayList<Partner> getPartners();

    //查找并返回合作方
    Partner findPartner(Partner partner);

    //设置当前登陆合作方
    void setCurrentPartner(Partner partner);

    //存储合作方到文件Partners.json中
    void store();
}
