package com.neu.dao;

import com.neu.pojo.UsingRecord;

public interface IUsingRecordDao {
    //添加使用记录文件
    void addUsingRecord(UsingRecord record);

    //保存使用记录
    void storeUsingRecord();
}
