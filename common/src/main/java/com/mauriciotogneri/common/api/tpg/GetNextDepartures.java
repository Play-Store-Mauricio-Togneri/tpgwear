package com.mauriciotogneri.common.api.tpg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetNextDepartures
{
    public String timestamp = "";
    public Stop stop = null;
    public List<Departure> departures = new ArrayList<>();

    public void setColors(GetLinesColors linesColors)
    {
        for (Departure departure : departures)
        {
            departure.setColor(linesColors.getColor(departure.line.lineCode));
        }
    }

    public void removeInvalidDepartures()
    {
        for (Iterator<Departure> iterator = departures.iterator(); iterator.hasNext(); )
        {
            Departure departure = iterator.next();

            if (departure.isInvalid())
            {
                iterator.remove();
            }
        }
    }
}