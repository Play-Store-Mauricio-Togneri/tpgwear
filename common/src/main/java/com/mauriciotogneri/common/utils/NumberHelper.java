package com.mauriciotogneri.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberHelper
{
    public static float round(double value, int decimals)
    {
        BigDecimal bd = new BigDecimal(value).setScale(decimals, RoundingMode.HALF_EVEN);

        return bd.floatValue();
    }
}