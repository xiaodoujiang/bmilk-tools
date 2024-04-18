package cn.bmilk.tools.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class GsonUtils {

    private final static Gson gson = new Gson();

    public static String toJson(Object object){
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> tClass){
        return gson.fromJson(json, tClass);
    }

    public static <T> T fromJson(String json, TypeToken<T> typeOfT){
        return gson.fromJson(json, typeOfT);
    }
    public static <T> T fromJson(String json, Type type){
        return gson.fromJson(json, type);
    }

}
