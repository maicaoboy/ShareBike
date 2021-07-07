package com.neu.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.dao.IUsingRecordDao;
import com.neu.pojo.CustomerServer;
import com.neu.pojo.UsingRecord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    @Override
    public void addUsingRecord(UsingRecord record) {
        records.add(record);
    }

    @Override
    public void storeUsingRecord() {
        try {
            File file = new File("UsingRecords.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                om.writeValue(file,records);;
            }

        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
