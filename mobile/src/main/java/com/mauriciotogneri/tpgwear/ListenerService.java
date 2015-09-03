package com.mauriciotogneri.tpgwear;

import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.concurrent.TimeUnit;

public class ListenerService extends WearableListenerService
{
    private String nodeId;

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        nodeId = messageEvent.getSourceNodeId();

        showToast(messageEvent.getPath());
    }

    private void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        reply("DEMOR!");
    }

    private void reply(String message)
    {
        GoogleApiClient client = new GoogleApiClient.Builder(this).addApi(Wearable.API).build();
        client.blockingConnect(1000 * 10, TimeUnit.MILLISECONDS);
        Wearable.MessageApi.sendMessage(client, nodeId, message, null);
        client.disconnect();
    }
}