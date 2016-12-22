package com.animecap.system.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nathaniel on 12/18/2016.
 */
public class FormatMap{
    public static HashMap<String, String> ConvertObjectToMap(Object obj) throws IllegalAccessException, IllegalArgumentException{
        Class<?> pomclass = obj.getClass();
        Field[] fields = obj.getClass().getDeclaredFields();
        HashMap<String, String> map = new HashMap<String, String>();
        for(Field f: fields){
            map.put(f.getName(),String.valueOf(f.get(obj)));
        }
        return map;
    }
}
