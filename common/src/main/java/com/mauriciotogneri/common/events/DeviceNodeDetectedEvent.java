package com.mauriciotogneri.common.events;

public class DeviceNodeDetectedEvent
{
    public final String nodeId;

    public DeviceNodeDetectedEvent(String nodeId)
    {
        this.nodeId = nodeId;
    }
}