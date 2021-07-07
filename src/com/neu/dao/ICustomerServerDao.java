package com.neu.dao;

import com.neu.pojo.CustomerServer;

import java.util.ArrayList;

public interface ICustomerServerDao {
    boolean addCustomerServer(CustomerServer customerServer);
    ArrayList<CustomerServer> getCustomerServers();
    CustomerServer findCustomerServer(CustomerServer customerServer);
}
