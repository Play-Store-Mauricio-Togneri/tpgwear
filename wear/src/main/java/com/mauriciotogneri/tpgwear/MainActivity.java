package com.mauriciotogneri.tpgwear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;

import com.mauriciotogneri.common.Connection;
import com.mauriciotogneri.common.Connection.ConnectivityEvents;
import com.mauriciotogneri.common.Connection.MessageResult;

public class MainActivity extends Activity implements ConnectivityEvents
{
    private String nodeId = null;
    private Connection connection = null;

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

        this.connection = new Connection(this, this);
        this.connection.retrieveDeviceNode();
    }

    @Override
    public void onNodeDetected(String nodeId)
    {
        this.nodeId = nodeId;

        connection.sendMessage(nodeId, "GET_STOPS", null, new MessageResult()
        {
            @Override
            public void onSuccess()
            {
                System.out.println("");
            }

            @Override
            public void onFailure()
            {
                System.out.println();
            }
        });
    }
}