package com.neu.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.dao.IUsingRecordDao;
import com.neu.pojo.UsingRecord;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用记录操作文件
 */

public class UsingRecordDao implements IUsingRecordDao {
    private ArrayList<UsingRecord> records;
    private static UsingRecordDao instance;

    public static UsingRecordDao getInstance() {
        if(instance == null) {
            instance = new UsingRecordDao();
        }
        return instance;
    }


    private UsingRecordDao() {
        records = new ArrayList<UsingRecord>();
        try {
            File file = new File("UsingRecords.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                records = om.readValue(file,new TypeReference<List<UsingRecord>>(){});
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }


    //添加使用记录文件
    @Override
    public void addUsingRecord(UsingRecord record) {
        records.add(record);
    }

    //保存使用记录
    @Override
    public void storeUsingRecord() {
        try {
            File file = new File("UsingRecords.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                om.writeValue(file,records);
            }

        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
