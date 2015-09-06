package com.mauriciotogneri.tpgwear;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.wearable.view.WatchViewStub;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.mauriciotogneri.common.Message;
import com.mauriciotogneri.common.WearableConnectivity;
import com.mauriciotogneri.common.WearableConnectivity.OnConnectionEvent;
import com.mauriciotogneri.common.WearableConnectivity.OnDeviceNodeDetected;
import com.mauriciotogneri.common.WearableConnectivity.OnMessageReceived;
import com.mauriciotogneri.common.api.WearableApi.Calls;

public class MainActivity extends Activity implements OnConnectionEvent, OnMessageReceived
{
    private WearableConnectivity wearableConnectivity;

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

        wearableConnectivity = new WearableConnectivity(this, this, this);
        wearableConnectivity.connect();
    }

    private void requestFavoriteStops(final String nodeId)
    {
        wearableConnectivity.sendMessage(Calls.getFavoriteStops(nodeId), new ResultCallback<SendMessageResult>()
        {
            @Override
            public void onResult(SendMessageResult sendMessageResult)
            {
                toast("MESSAGE SENT: " + sendMessageResult.getStatus().isSuccess() + " -> " + nodeId);
            }
        });
    }

    @Override
    public void onMessageReceived(Message message)
    {
        toast(message.getPayloadAsString());
    }

    @Override
    public void onConnectedSuccess()
    {
        //toast("CLIENT CONNECTED");

        wearableConnectivity.getDefaultDeviceNode(new OnDeviceNodeDetected()
        {
            @Override
            public void onDefaultDeviceNode(String nodeId)
            {
                requestFavoriteStops(nodeId);
            }
        });
    }

    @Override
    public void onConnectedFail()
    {
        toast("CLIENT DISCONNECTED");
    }

    private void toast(final String message)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}