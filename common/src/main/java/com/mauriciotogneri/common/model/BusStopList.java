package com.mauriciotogneri.common.model;

import com.mauriciotogneri.common.utils.JsonUtils;

import java.util.ArrayList;

public class BusStopList extends ArrayList<BusStop>
{
    public static BusStopList fromString(String input)
    {
        return JsonUtils.fromJson(input, BusStopList.class);
    }

    @Override
    public String toString()
    {
        return JsonUtils.toJson(this);
    }
}