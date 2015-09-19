package com.mauriciotogneri.common.utils;

import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;

public class JsonUtils
{
    private static final Gson gson = new Gson();

    public static <T> T fromJson(String input, Class<T> clazz)
    {
        return gson.fromJson(input, clazz);
    }

    public static <T> T fromJson(String input, Type type)
    {
        return gson.fromJson(input, type);
    }

    public static <T> T fromJson(Reader input, Class<T> clazz)
    {
        return gson.fromJson(input, clazz);
    }

    public static String toJson(Object object)
    {
        return gson.toJson(object);
    }
}