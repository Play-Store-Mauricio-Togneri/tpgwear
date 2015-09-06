package com.mauriciotogneri.common.model;

public class BusStopDeparture
{
    private BusLine busLine;
    private String destination = "";
    private String waitingTime = "";

    public BusStopDeparture(BusLine busLine, String destination, String waitingTime)
    {
        this.busLine = busLine;
        this.destination = destination;
        this.waitingTime = waitingTime;
    }

    public BusLine getBusLine()
    {
        return busLine;
    }

    public String getDestination()
    {
        return destination;
    }

    public String getWaitingTime()
    {
        try
        {
            int milliseconds = Integer.parseInt(waitingTime);

            return String.valueOf(milliseconds / 1000);
        }
        catch (Exception e)
        {
            return waitingTime;
        }
    }
}