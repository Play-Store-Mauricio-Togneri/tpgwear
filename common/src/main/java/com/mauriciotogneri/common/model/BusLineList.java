package com.mauriciotogneri.common.model;

import android.text.TextUtils;

import com.mauriciotogneri.common.utils.JsonUtils;

import java.util.ArrayList;

public class BusLineList extends ArrayList<BusLine>
{
    public static BusLineList fromString(String input)
    {
        return JsonUtils.fromJson(input, BusLineList.class);
    }

    public BusLine getByName(String name)
    {
        for (BusLine busLine : this)
        {
            if (TextUtils.equals(busLine.getName(), name))
            {
                return busLine;
            }
        }

        return null;
    }

    @Override
    public String toString()
    {
        return JsonUtils.toJson(this);
    }
}