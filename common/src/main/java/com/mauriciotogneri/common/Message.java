package com.mauriciotogneri.common;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

public class Message
{
    private final String nodeId;
    private final String path;
    private final String payload;

    private static final String ENCODING = "UTF-8";

    public Message(String nodeId, String path, String payload)
    {
        this.nodeId = nodeId;
        this.path = path;
        this.payload = payload;
    }

    public Message(String nodeId, String path, byte[] payload)
    {
        this.nodeId = nodeId;
        this.path = path;
        this.payload = getPayLoad(payload);
    }

    public Message(String nodeId, String path)
    {
        this(nodeId, path, "");
    }

    public String getNodeId()
    {
        return nodeId;
    }

    public String getPath()
    {
        return path;
    }

    public String getPayloadAsString()
    {
        return payload;
    }

    public byte[] getPayloadAsBytes()
    {
        if (TextUtils.isEmpty(payload))
        {
            return new byte[0];
        }
        else
        {
            try
            {
                return payload.getBytes(ENCODING);
            }
            catch (UnsupportedEncodingException e)
            {
                return new byte[0];
            }
        }
    }

    private String getPayLoad(byte[] data)
    {
        if ((data == null) || (data.length == 0))
        {
            return null;
        }
        else
        {
            try
            {
                return new String(data, ENCODING);
            }
            catch (UnsupportedEncodingException e)
            {
                return null;
            }
        }
    }
}