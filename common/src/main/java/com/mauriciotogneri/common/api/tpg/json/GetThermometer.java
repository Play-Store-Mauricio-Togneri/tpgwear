package com.mauriciotogneri.common.api.tpg.json;

import java.util.ArrayList;
import java.util.List;

public class GetThermometer
{
    public String timestamp = "";
    public Stop stop = null;
    public String lineCode = "";
    public String destinationName = "";
    public String destinationCode = "";
    public List<Step> steps = new ArrayList<>();
}