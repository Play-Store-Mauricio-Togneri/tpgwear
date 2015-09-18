package com.mauriciotogneri.common.api.tpg.json;

import java.util.ArrayList;
import java.util.List;

public class GetNextDepartures
{
    public String timestamp = "";
    public Stop stop = null;
    public List<Departure> departures = new ArrayList<>();
}