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
        wearableConnectivity.getDeviceNode(new OnDeviceNodeDetected()
        {
            @Override
            public void onDeviceNodeDetected(String nodeId)
            {
                deviceDetected(nodeId);
            }
        });
    }

    private void deviceDetected(String nodeId)
    {
        Message message = new Message(nodeId, "GET_STOPS");

        wearableConnectivity.sendMessage(message, new ResultCallback<SendMessageResult>()
        {
            @Override
            public void onResult(SendMessageResult sendMessageResult)
            {
                toast("MESSAGE SENT: " + sendMessageResult.getStatus().isSuccess());
            }
        });
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
        toast(message.getPayloadAsString());
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