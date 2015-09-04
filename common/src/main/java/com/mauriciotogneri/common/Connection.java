package com.mauriciotogneri.common;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Connection
{
    private final GoogleApiClient apiClient;
    private final ConnectivityEvents connectivityEvents;

    private static final int TIMEOUT = 1000 * 10;

    public Connection(Context context, ConnectivityEvents connectivityEvents)
    {
        this.apiClient = new GoogleApiClient.Builder(context).addApi(Wearable.API).build();
        this.connectivityEvents = connectivityEvents;
    }

    public synchronized void sendMessage(final String nodeId, final String path, final String payload, final MessageResult callback)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                apiClient.blockingConnect(TIMEOUT, TimeUnit.MILLISECONDS);

                try
                {
                    Wearable.MessageApi.sendMessage(apiClient, nodeId, path, (payload != null) ? payload.getBytes("UTF-8") : null).setResultCallback(new ResultCallback<SendMessageResult>()
                    {
                        @Override
                        public void onResult(SendMessageResult sendMessageResult)
                        {
                            if (sendMessageResult.getStatus().isSuccess())
                            {
                                callback.onSuccess();
                            }
                            else
                            {
                                callback.onFailure();
                            }
                        }
                    });
                }
                catch (Exception e)
                {
                    callback.onFailure();
                }

                apiClient.disconnect();
            }
        }).start();
    }

    public synchronized void retrieveDeviceNode()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String nodeId = null;

                apiClient.blockingConnect(TIMEOUT, TimeUnit.MILLISECONDS);
                NodeApi.GetConnectedNodesResult result = Wearable.NodeApi.getConnectedNodes(apiClient).await();
                List<Node> nodes = result.getNodes();

                if (nodes.size() > 0)
                {
                    nodeId = nodes.get(0).getId();
                }
                apiClient.disconnect();

                connectivityEvents.onNodeDetected(nodeId);
            }
        }).start();
    }

    public interface ConnectivityEvents
    {
        void onNodeDetected(String nodeId);
    }

    public interface MessageResult
    {
        void onSuccess();

        void onFailure();
    }
}