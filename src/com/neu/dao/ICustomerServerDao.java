package com.neu.dao;

import com.neu.pojo.CustomerServer;

import java.util.ArrayList;

/**
 * 客服数据操作接口
 */
public interface ICustomerServerDao {
    //添加一个客服
    boolean addCustomerServer(CustomerServer customerServer);

    //取得客服集合
    ArrayList<CustomerServer> getCustomerServers();

    //由仅含用户名和密码的假对象取得含具体信息的真正客服
    CustomerServer findCustomerServer(CustomerServer customerServer);

    //保存客服数据
    void store();
}
