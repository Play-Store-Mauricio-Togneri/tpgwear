package com.mauriciotogneri.common.api.tpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stop
{
    public String stopCode = "";
    public String stopName = "";
    public int hitCount = 0;
    public boolean favorite = false;
    public List<Line> connections = new ArrayList<>();

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Stop stop = (Stop) o;

        return Objects.equals(stopCode, stop.stopCode);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(stopCode);
    }
}