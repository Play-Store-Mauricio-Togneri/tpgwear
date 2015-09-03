package com.mauriciotogneri.tpgwear;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity
{
    private GoogleApiClient client;
    private String nodeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener()
        {
            @Override
            public void onLayoutInflated(WatchViewStub stub)
            {
            }
        });

        client = getGoogleApiClient(this);
        retrieveDeviceNode();
    }

    private void sendToast()
    {
        if (nodeId != null)
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    client.blockingConnect(1000 * 10, TimeUnit.MILLISECONDS);
                    Wearable.MessageApi.sendMessage(client, nodeId, "AKANT!", null);
                    client.disconnect();
                }
            }).start();
        }
    }

    private void retrieveDeviceNode()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                client.blockingConnect(1000 * 10, TimeUnit.MILLISECONDS);
                NodeApi.GetConnectedNodesResult result = Wearable.NodeApi.getConnectedNodes(client).await();
                List<Node> nodes = result.getNodes();
                if (nodes.size() > 0)
                {
                    nodeId = nodes.get(0).getId();
                }
                client.disconnect();

                sendToast();
            }
        }).start();
    }

    private GoogleApiClient getGoogleApiClient(Context context)
    {
        return new GoogleApiClient.Builder(context).addApi(Wearable.API).build();
    }
}