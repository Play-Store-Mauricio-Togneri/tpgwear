package com.mauriciotogneri.common.model;

import com.mauriciotogneri.common.utils.JsonUtils;

import java.util.ArrayList;

public class BusStopDepartureList extends ArrayList<BusStopDeparture>
{
    public static BusStopDepartureList fromString(String input)
    {
        return JsonUtils.fromJson(input, BusStopDepartureList.class);
    }

    @Override
    public String toString()
    {
        return JsonUtils.toJson(this);
    }
}