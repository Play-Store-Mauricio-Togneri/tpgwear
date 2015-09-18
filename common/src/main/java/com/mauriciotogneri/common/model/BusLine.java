package com.mauriciotogneri.common.model;

public class BusLine
{
    private String code = "";
    private String destinationName = "";
    private String destinationCode = "";
    private String color = "";

    public BusLine(String code, String destinationName, String destinationCode, String color)
    {
        this.code = code;
        this.destinationName = destinationName;
        this.destinationCode = destinationCode;
        this.color = color;
    }

    public String getCode()
    {
        return code;
    }

    public String getDestinationName()
    {
        return destinationName;
    }

    public String getDestinationCode()
    {
        return destinationCode;
    }

    public String getColor()
    {
        return color;
    }
}