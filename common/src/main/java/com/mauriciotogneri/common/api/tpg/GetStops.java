package com.mauriciotogneri.common.api.tpg;

import java.util.ArrayList;
import java.util.List;

public class GetStops
{
    public String timestamp = "";
    public List<Stop> stops = new ArrayList<>();

    public void setColors(GetLinesColors linesColors)
    {
        for (Stop stop : stops)
        {
            stop.setColors(linesColors);
        }
    }
}