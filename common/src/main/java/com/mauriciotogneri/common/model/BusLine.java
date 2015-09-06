package com.mauriciotogneri.common.model;

public class BusLine
{
    private String name = "";
    private String color = "";

    public BusLine(String name, String color)
    {
        this.name = name;
        this.color = color;
    }

    public String getName()
    {
        return name;
    }

    public String getColor()
    {
        return color;
    }
}