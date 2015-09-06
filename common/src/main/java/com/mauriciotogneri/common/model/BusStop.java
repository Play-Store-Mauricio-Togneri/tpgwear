package com.mauriciotogneri.common.model;

import java.util.ArrayList;
import java.util.List;

public class BusStop
{
    private String name = "";
    private String code = "";
    private List<BusLine> lines = new ArrayList<>();

    public BusStop(String name, String code)
    {
        this.name = name;
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }

    public void addLine(BusLine busLine)
    {
        lines.add(busLine);
    }
}