package com.mauriciotogneri.common.api.tpg;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class GetLinesColors
{
    public String timestamp = "";
    public List<Color> colors = new ArrayList<>();

    public String getColor(String lineCode)
    {
        for (Color color : colors)
        {
            if (TextUtils.equals(color.lineCode, lineCode))
            {
                return color.hexa;
            }
        }

        return "000000";
    }
}