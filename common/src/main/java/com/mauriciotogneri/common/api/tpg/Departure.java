package com.mauriciotogneri.common.api.tpg;

import android.text.TextUtils;

public class Departure
{
    public int departureCode = 0;
    public String timestamp = "";
    public int waitingTimeMillis = 0;
    public String waitingTime = "";
    public Line line = null;
    public String reliability = "";
    public String characteristics = "";
    public String vehiculeType = "";
    public int vehiculeNo = 0;
    public String color = "";

    public void setColor(String color)
    {
        this.color = color;
    }

    public boolean isInvalid()
    {
        return TextUtils.equals(waitingTime, "no more");
    }

    public String getFormattedWaitingTime()
    {
        if (TextUtils.equals(waitingTime, "&gt;1h"))
        {
            return ">1h";
        }
        else
        {
            return waitingTime + "'";
        }
    }
}