package com.neu.dao;

import com.neu.pojo.Partner;

import java.util.ArrayList;

public interface IPartnerDao {
    boolean addPartner(Partner partner);
    ArrayList<Partner> getPartners();
    Partner findPartner(Partner partner);
    void setCurrentPartner(Partner partner);
    void store();
}
