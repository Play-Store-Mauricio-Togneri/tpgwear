package com.mauriciotogneri.tpgwear;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.support.wearable.view.WearableListView.ClickListener;
import android.support.wearable.view.WearableListView.ViewHolder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mauriciotogneri.common.WearableConnectivity;
import com.mauriciotogneri.common.WearableConnectivity.OnConnectionEvent;
import com.mauriciotogneri.common.WearableConnectivity.OnDeviceNodeDetected;
import com.mauriciotogneri.common.WearableConnectivity.OnMessageReceived;
import com.mauriciotogneri.common.api.WearableApi.Calls;
import com.mauriciotogneri.common.api.WearableApi.Paths;
import com.mauriciotogneri.common.model.BusStop;
import com.mauriciotogneri.common.model.BusStopList;
import com.mauriciotogneri.common.model.Message;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.mauriciotogneri.tpgwear.BusStopAdapter.BusStopViewHolder;

public class MainActivity extends Activity implements OnConnectionEvent, OnMessageReceived
{
    private WearableConnectivity wearableConnectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wearableConnectivity = new WearableConnectivity(this, this, this);

        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener()
        {
            @Override
            public void onLayoutInflated(WatchViewStub stub)
            {
                wearableConnectivity.connect();
            }
        });
    }

    private void requestFavoriteStops(final String nodeId)
    {
        wearableConnectivity.sendMessage(Calls.getFavoriteStops(nodeId));
    }

    @Override
    public void onMessageReceived(Message message)
    {
        if (TextUtils.equals(message.getPath(), Paths.RESULT_FAVORITE_STOPS))
        {
            BusStopList busStopList = JsonUtils.fromJson(message.getPayloadAsString(), BusStopList.class);
            processResultFavoriteStops(busStopList);
        }
    }

    private void processResultFavoriteStops(BusStopList busStopList)
    {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        WearableListView wearableListView = (WearableListView) findViewById(R.id.bus_stop_list);
        wearableListView.setAdapter(new BusStopAdapter(this, busStopList));
        wearableListView.setVisibility(View.VISIBLE);
        wearableListView.setClickListener(new ClickListener()
        {
            @Override
            public void onClick(ViewHolder viewHolder)
            {
                BusStopViewHolder busStopViewHolder = (BusStopViewHolder) viewHolder;
                onBusStopSelected(busStopViewHolder.getBusStop());
            }

            @Override
            public void onTopEmptyRegionClick()
            {
            }
        });
    }

    private void onBusStopSelected(BusStop busStop)
    {
        toast(busStop.getName());
    }

    @Override
    public void onConnectedSuccess()
    {
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