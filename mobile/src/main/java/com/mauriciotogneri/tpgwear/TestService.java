package com.mauriciotogneri.tpgwear;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.mauriciotogneri.common.Message;
import com.mauriciotogneri.common.WearableConnectivity;
import com.mauriciotogneri.common.WearableConnectivity.OnConnectionEvent;
import com.mauriciotogneri.common.WearableConnectivity.OnMessageReceived;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class TestService extends Service implements OnConnectionEvent, OnMessageReceived
{
    private final OkHttpClient client = new OkHttpClient();
    private WearableConnectivity wearableConnectivity;

    @Override
    public void onCreate()
    {
        super.onCreate();

        wearableConnectivity = new WearableConnectivity(this, this, this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onConnectedSuccess()
    {
        System.out.println();
    }

    @Override
    public void onConnectedFail()
    {
        System.out.println();
    }

    @Override
    public void onMessageReceived(Message message)
    {
        toast("MESSAGE RECEIVED");

        String nodeId = message.getNodeId();
        String path = message.getPath();
        byte[] payload = message.getPayloadAsBytes();

        if (path.equals("GET_STOPS"))
        {
            getStops(nodeId);
        }
    }

    private void getStops(final String nodeId)
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
                        processGetStops(nodeId, result);
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

    private void processGetStops(String nodeId, String result)
    {
        Message message = new Message(nodeId, "RESULT_STOPS", result);

        wearableConnectivity.sendMessage(message, new ResultCallback<SendMessageResult>()
        {
            @Override
            public void onResult(SendMessageResult sendMessageResult)
            {
                toast("MESSAGE SENT: " + sendMessageResult.getStatus().isSuccess());
            }
        });
    }

    private void toast(final String message)
    {
        Log.e("ERROR", message);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(TestService.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}