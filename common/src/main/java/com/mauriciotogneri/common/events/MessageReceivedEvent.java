package com.mauriciotogneri.common.events;

import com.mauriciotogneri.common.Message;

public class MessageReceivedEvent
{
    public final Message message;

    public MessageReceivedEvent(Message message)
    {
        this.message = message;
    }
}