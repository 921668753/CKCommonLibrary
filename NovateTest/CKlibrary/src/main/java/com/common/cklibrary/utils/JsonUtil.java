package com.common.cklibrary.utils;


import com.google.gson.Gson;
import com.google.gson.internal.Primitives;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/5/27.
 */
public class JsonUtil {
    private static JsonUtil jsonUtil = null;
    private static Gson gson;


    private JsonUtil() {
        gson = new Gson();
    }

    public synchronized static JsonUtil getInstance() {
        if (jsonUtil == null) {
            jsonUtil = new JsonUtil();
        }
        return jsonUtil;
    }


    public static Gson getGson() {
        return gson;
    }


    public static String obj2JsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T json2Obj(String jsonString, Class<T> clzz) {
        try {
            T object = gson.fromJson(jsonString, clzz);
            return Primitives.wrap(clzz).cast(object);
        } catch (Exception e) {
            return null;
        }
    }


    public static <T> T fromJson(String jsonString, Class<T> clzz) throws Exception {
        T object = gson.fromJson(jsonString, clzz);
        return Primitives.wrap(clzz).cast(object);
    }

    public static boolean checkObjFieldIsNull(Object obj) {
        boolean flag = false;
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(obj) == null) {
                    flag = true;
                    return flag;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            flag = true;
            return flag;
        }
        return flag;
    }
}
