package com.neu.utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class Util {
    private static Properties properties = new Properties();

    static {
        try{
            properties.load(Util.class.getClassLoader().getResourceAsStream("init.properties"));
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static Object getObject(String key) {
        String value = properties.getProperty(key);
        Object object = null;
        try{
            Class clazz = Class.forName(value);
            Method method = clazz.getMethod("getInstance");
            object = method.invoke(null);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return object;
    }

/*
    public static void main(String[] args) {
        System.out.println(Util.getObject("BikeDao"));
    }
*/
}
