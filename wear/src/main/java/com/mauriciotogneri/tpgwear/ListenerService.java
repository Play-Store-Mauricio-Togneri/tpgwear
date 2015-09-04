package com.mauriciotogneri.tpgwear;

import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class ListenerService extends WearableListenerService
{
    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        String nodeId = messageEvent.getSourceNodeId();
        String path = messageEvent.getPath();
        String payload = new String(messageEvent.getData());

        Toast.makeText(this, payload, Toast.LENGTH_SHORT).show();
    }
}