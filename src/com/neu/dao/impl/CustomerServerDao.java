package com.neu.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.dao.ICustomerServerDao;
import com.neu.pojo.CustomerServer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServerDao implements ICustomerServerDao {
    CustomerServerDao currentServer;
    ArrayList<CustomerServer> servers;
    private static CustomerServerDao instance;

    private CustomerServerDao() {
        currentServer = null;servers = new ArrayList<CustomerServer>();
        try {
            File file = new File("CustomerServers.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                servers = om.readValue(file,new TypeReference<List<CustomerServer>>(){});
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static CustomerServerDao getInstance() {
        if(instance == null) {
            instance = new CustomerServerDao();
        }
        return instance;
    }

    @Override
    public boolean addCustomerServer(CustomerServer customerServer) {
        boolean result = true;
        for(CustomerServer server : servers) {
            if(server.equals(customerServer)) {
                result = false;
                break;
            }
        }
        if(result) {
            servers.add(customerServer);
        }
        return result;

    }

    @Override
    public ArrayList<CustomerServer> getCustomerServers() {
        return servers;
    }

    @Override
    public CustomerServer findCustomerServer(CustomerServer customerServer) {
        CustomerServer customerServer1 = null;
        for(CustomerServer server : servers) {
            if(customerServer.equals(server)) {
                customerServer1 = server;
            }
        }
        return customerServer1;
    }

    public void store() {
        try {
            File file = new File("CustomerServers.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                om.writeValue(file,servers);;
            }

        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }


}
