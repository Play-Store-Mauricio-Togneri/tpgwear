package com.mauriciotogneri.common.api.tpg;

import android.text.TextUtils;

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

    public List<Line> getLines()
    {
        List<Line> result = new ArrayList<>();

        for (Line line : connections)
        {
            if (!containsLine(result, line))
            {
                result.add(line);
            }
        }

        return result;
    }

    private boolean containsLine(List<Line> lines, Line line)
    {
        for (Line current : lines)
        {
            if (TextUtils.equals(current.lineCode, line.lineCode))
            {
                return true;
            }
        }

        return false;
    }

    public void setColors(GetLinesColors linesColors)
    {
        for (Line line : connections)
        {
            line.color = linesColors.getColor(line.lineCode);
        }
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(stopCode);
    }
}