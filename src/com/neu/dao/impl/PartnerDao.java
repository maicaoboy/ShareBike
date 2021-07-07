package com.neu.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.dao.IPartnerDao;
import com.neu.pojo.Customer;
import com.neu.pojo.Partner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PartnerDao implements IPartnerDao {
    ArrayList<Partner> partners;
    Partner currentPartner;
    private static PartnerDao instance;

    public static PartnerDao getInstance() {
        if(instance == null) {
            instance = new PartnerDao();
        }
        return instance;
    }

    @Override
    public boolean addPartner(Partner partner) {
        boolean result = true;
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

    @Override
    public ArrayList<Partner> getPartners() {
        return partners;
    }

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

    @Override
    public void setCurrentPartner(Partner partner) {
        this.currentPartner = partner;
    }

    @Override
    public void store() {
        try {
            File file = new File("Partners.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                om.writeValue(file,partners);;
            }

        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
