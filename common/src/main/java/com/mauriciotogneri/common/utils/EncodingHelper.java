package com.mauriciotogneri.common.utils;

import android.text.TextUtils;

public class EncodingHelper
{
    private static final String ISO_8859_1 = "ISO-8859-1";
    private static final String UTF_8 = "UTF-8";

    public static String decodeFromIso(String input)
    {
        return getString(input, ISO_8859_1, UTF_8);
    }

    public static String getString(String input, String charsetInput, String charsetOutput)
    {
        try
        {
            byte[] isoBytes = input.getBytes(charsetInput);

            return new String(isoBytes, charsetOutput);
        }
        catch (Exception e)
        {
            return input;
        }
    }

    public static byte[] getStringAsBytes(String input)
    {
        if (TextUtils.isEmpty(input))
        {
            return new byte[0];
        }
        else
        {
            try
            {
                return input.getBytes(EncodingHelper.UTF_8);
            }
            catch (Exception e)
            {
                return new byte[0];
            }
        }
    }

    public static String getBytesAsString(byte[] data)
    {
        if ((data == null) || (data.length == 0))
        {
            return null;
        }
        else
        {
            try
            {
                return new String(data, EncodingHelper.UTF_8);
            }
            catch (Exception e)
            {
                return null;
            }
        }
    }
}