package com.mauriciotogneri.common;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import de.greenrobot.event.EventBus;

public class WearableService extends WearableListenerService
{
    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        String nodeId = messageEvent.getSourceNodeId();
        String path = messageEvent.getPath();
        byte[] payload = messageEvent.getData();

        EventBus.getDefault().post(new Message(nodeId, path, payload));
    }
}