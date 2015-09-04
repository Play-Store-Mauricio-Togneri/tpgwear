package com.mauriciotogneri.tpgwear;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.mauriciotogneri.common.Connection;
import com.mauriciotogneri.common.Connection.ConnectivityEvents;
import com.mauriciotogneri.common.Connection.MessageResult;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class ListenerService extends WearableListenerService implements ConnectivityEvents
{
    private Connection connection;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.connection = new Connection(this, this);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        String nodeId = messageEvent.getSourceNodeId();
        String path = messageEvent.getPath();

        getAllStops(nodeId);
    }

    private void getAllStops(final String nodeId)
    {
        Request.Builder builder = new Request.Builder();
        builder.url("http://pitre-iphone.tpg.ch/GetTousArrets.json");

        Request request = builder.build();

        try
        {
            client.newCall(request).enqueue(new Callback()
            {
                @Override
                public void onResponse(Response response) throws IOException
                {
                    if (response.isSuccessful())
                    {
                        String result = response.body().string();

                        sendData(nodeId, "RESULT_STOPS", result);
                    }
                    else
                    {
                        System.out.println();
                    }
                }

                @Override
                public void onFailure(Request request, IOException e)
                {
                    System.out.println();
                }
            });
        }
        catch (Exception e)
        {
            System.out.println();
        }
    }

    @Override
    public void onNodeDetected(String nodeId)
    {
    }

    private void sendData(String nodeId, String path, String payload)
    {
        connection.sendMessage(nodeId, path, payload, new MessageResult()
        {
            @Override
            public void onSuccess()
            {
                System.out.println();
            }

            @Override
            public void onFailure()
            {
                System.out.println();
            }
        });
    }
}