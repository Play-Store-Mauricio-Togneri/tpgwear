package com.mauriciotogneri.common.api.tpg.json;

import java.util.ArrayList;
import java.util.List;

public class Stop
{
    public String stopCode = "";
    public String stopName = "";
    public List<Line> connections = new ArrayList<>();
}