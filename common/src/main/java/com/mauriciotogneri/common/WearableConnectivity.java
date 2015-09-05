package com.mauriciotogneri.common;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageApi.MessageListener;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.TimeUnit;

public class WearableConnectivity
{
    private boolean isConnected = false;
    private final GoogleApiClient apiClient;

    private static final int TIMEOUT = 1000 * 10;

    public WearableConnectivity(Context context, final OnConnectionEvent onConnectionEvent, final OnMessageReceived onMessageReceived)
    {
        apiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(new ConnectionCallbacks()
        {
            @Override
            public void onConnected(Bundle connectionHint)
            {
                if (!isConnected)
                {
                    isConnected = true;

                    if (onMessageReceived != null)
                    {
                        new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try
                                {
                                    PendingResult<Status> pendingResult = Wearable.MessageApi.addListener(apiClient, new MessageListener()
                                    {
                                        @Override
                                        public void onMessageReceived(MessageEvent messageEvent)
                                        {
                                            String nodeId = messageEvent.getSourceNodeId();
                                            String path = messageEvent.getPath();
                                            byte[] payload = messageEvent.getData();

                                            Message message = new Message(nodeId, path, payload);
                                            onMessageReceived.onMessageReceived(message);
                                        }
                                    });
                                    pendingResult.await();
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }

                    if (onConnectionEvent != null)
                    {
                        onConnectionEvent.onConnectedSuccess();
                    }
                }
            }

            @Override
            public void onConnectionSuspended(int cause)
            {
                if (onConnectionEvent != null)
                {
                    onConnectionEvent.onConnectedFail();
                }
            }
        }).addOnConnectionFailedListener(new OnConnectionFailedListener()
        {
            @Override
            public void onConnectionFailed(ConnectionResult result)
            {
                if (onConnectionEvent != null)
                {
                    onConnectionEvent.onConnectedFail();
                }
            }
        }).addApi(Wearable.API).build();

        apiClient.connect();
    }

    public synchronized void sendMessage(final Message message, final ResultCallback<SendMessageResult> callback)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    apiClient.blockingConnect(TIMEOUT, TimeUnit.MILLISECONDS);

                    PendingResult<MessageApi.SendMessageResult> pendingResult = Wearable.MessageApi.sendMessage(apiClient, message.getNodeId(), message.getPath(), message.getPayloadAsBytes());
                    pendingResult.setResultCallback(callback);
                    pendingResult.await();

                    apiClient.disconnect();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public synchronized void getDeviceNode(final OnDeviceNodeDetected onDeviceNodeDetected)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                apiClient.blockingConnect(TIMEOUT, TimeUnit.MILLISECONDS);

                NodeApi.GetConnectedNodesResult result = Wearable.NodeApi.getConnectedNodes(apiClient).await();

                for (Node node : result.getNodes())
                {
                    if (node.isNearby())
                    {
                        onDeviceNodeDetected.onDeviceNodeDetected(node.getId());
                        break;
                    }
                }

                apiClient.disconnect();
            }
        }).start();
    }

    public interface OnMessageReceived
    {
        void onMessageReceived(Message message);
    }

    public interface OnConnectionEvent
    {
        void onConnectedSuccess();

        void onConnectedFail();
    }

    public interface OnDeviceNodeDetected
    {
        void onDeviceNodeDetected(String nodeId);
    }
}