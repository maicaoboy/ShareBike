package com.neu.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.dao.ICustomerServerDao;
import com.neu.pojo.CustomerServer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 客服数据操作对象
 */

public class CustomerServerDao implements ICustomerServerDao {
    CustomerServerDao currentServer;                //当前登录客服
    ArrayList<CustomerServer> servers;              //客服集合
    private static CustomerServerDao instance;

    private CustomerServerDao() {
        currentServer = null;
        servers = new ArrayList<CustomerServer>();
        //加载客服信息
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

    //添加一个客服
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


    //由仅含用户名和密码的假对象取得含具体信息的真正客服
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

    //保存客服数据
    @Override
    public void store() {
        try {
            File file = new File("CustomerServers.json");
                ObjectMapper om = new ObjectMapper();
                om.writeValue(file,servers);;
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }


}
