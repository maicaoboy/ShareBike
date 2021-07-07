package com.neu.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.dao.IBikeDao;
import com.neu.pojo.Bike;
import com.neu.pojo.Customer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BikeDao implements IBikeDao {
    private ArrayList<Bike> bikes;
    private static BikeDao instance;

    public static BikeDao getInstance() {
        if(instance == null) {
            instance = new BikeDao();
        }
        return instance;
    }

    private BikeDao() {
        bikes = new ArrayList<Bike>();
        try {
            File file = new File("Bikes.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                bikes = om.readValue(file,new TypeReference<List<Bike>>(){});
                int max = 0;
                for(Bike bike : bikes) {
                    if(bike.getID() > max) {
                        max = bike.getID();
                    }
                    Bike.setIdRecord(++max);
                }
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public ArrayList<Bike> getBikes() {
        return bikes;
    }

    @Override
    public Bike findBike(int id) {
        Bike bike1 = null;
        for(Bike bike : bikes) {
            if(bike.getID() == id) {
                bike1 = bike;
                break;
            }
        }
        return bike1;
    }

    public Bike findByID(int id) {
        Bike find = null;
        for(Bike bike : bikes) {
            if(bike.getID() == id) {
                find = bike;
            }
        }
        return find;
    }


    @Override
    public void storeBike() {
        try {
            File file = new File("Bikes.json");
            if(file.exists()) {
                ObjectMapper om = new ObjectMapper();
                om.writeValue(file,bikes);;
            }

        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
