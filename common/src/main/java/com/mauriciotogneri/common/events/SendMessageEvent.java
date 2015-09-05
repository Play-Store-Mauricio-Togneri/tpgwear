package com.mauriciotogneri.common.events;

import com.mauriciotogneri.common.Message;

public class SendMessageEvent
{
    public final Message message;

    public SendMessageEvent(Message message)
    {
        this.message = message;
    }
}