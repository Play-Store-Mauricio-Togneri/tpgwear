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

            if (milliseconds <= 0)
            {
                return "0'";
            }
            else
            {
                float seconds = milliseconds / 1000f;
                float minutes = (seconds / 60f);

                return ((int) Math.floor(minutes)) + "'";
            }
        }
        catch (Exception e)
        {
            return waitingTime;
        }
    }
}