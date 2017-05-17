package com.ckcommon.utils;


import com.google.gson.Gson;
import com.google.gson.internal.Primitives;

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

    public static String obj2JsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T json2Obj(String jsonString, Class<T> clzz) {
        Object object = gson.fromJson(jsonString, clzz);
        return Primitives.wrap(clzz).cast(object);
    }


    public static <T> T fromJson(String jsonString, Class<T> clzz) throws Exception {
        Object object = gson.fromJson(jsonString, clzz);
        return Primitives.wrap(clzz).cast(object);
    }
}
