package com.neu.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.dao.IPartnerDao;
import com.neu.pojo.Partner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartnerDao implements IPartnerDao {
    ArrayList<Partner> partners;        //存储合作方
    Partner currentPartner;             //存储当前登录合作方
    private static PartnerDao instance;

    public static PartnerDao getInstance() {
        if(instance == null) {
            instance = new PartnerDao();
        }
        return instance;
    }

    private PartnerDao() {
        partners = new ArrayList<Partner>();
        //第一次加载的时候恢复合作方数据
        try {
            File file = new File("Partners.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                partners = om.readValue(file,new TypeReference<List<Partner>>(){});
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    //添加合作方
    @Override
    public boolean addPartner(Partner partner) {
        //添加是否成功
        boolean result = true;
        //查找是否已注册
        for(Partner partner1 : partners) {
            if(partner1.equals(partner)) {
                result = false;
                break;
            }
        }
        if(result) {
            partners.add(partner);
        }
        return result;
    }

    //返回合作方集合
    @Override
    public ArrayList<Partner> getPartners() {
        return partners;
    }

    //查找并返回合作方
    @Override
    public Partner findPartner(Partner partner) {
        Partner partner1 = null;
        for(Partner partner2 : partners) {
            if(partner2.equals(partner)) {
                partner1 = partner2;
                break;
            }
        }
        return partner1;
    }

    //设置当前登陆合作方
    @Override
    public void setCurrentPartner(Partner partner) {
        this.currentPartner = partner;
    }

    //存储合作方到文件Partners.json中
    @Override
    public void store() {
        try {
            File file = new File("Partners.json");
                ObjectMapper om = new ObjectMapper();
                om.writeValue(file,partners);;

        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
