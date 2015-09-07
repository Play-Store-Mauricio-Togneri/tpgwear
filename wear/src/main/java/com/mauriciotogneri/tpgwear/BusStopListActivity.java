package com.mauriciotogneri.tpgwear;

import android.app.Activity;
import android.content.Intent;
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

import com.mauriciotogneri.common.api.wearable.WearableConnectivity;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.OnConnectionEvent;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.OnDeviceNodeDetected;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.OnMessageReceived;
import com.mauriciotogneri.common.api.wearable.WearableApi.Calls;
import com.mauriciotogneri.common.api.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.model.BusStop;
import com.mauriciotogneri.common.model.BusStopList;
import com.mauriciotogneri.common.model.BusStopDepartureList;
import com.mauriciotogneri.common.model.Message;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.mauriciotogneri.tpgwear.adapters.BusStopAdapter;
import com.mauriciotogneri.tpgwear.adapters.BusStopAdapter.BusStopViewHolder;

public class BusStopListActivity extends Activity implements OnConnectionEvent, OnMessageReceived
{
    private String nodeId = "";
    private WearableConnectivity wearableConnectivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stub_bus_stop_list);

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

    private void requestFavoriteStops()
    {
        wearableConnectivity.sendMessage(Calls.getFavoriteBusStops(nodeId));
    }

    @Override
    public void onMessageReceived(Message message)
    {
        if (TextUtils.equals(message.getPath(), Paths.RESULT_FAVORITE_BUS_STOPS))
        {
            BusStopList busStopList = JsonUtils.fromJson(message.getPayloadAsString(), BusStopList.class);
            processResultFavoriteStops(busStopList);
        }
        else if (TextUtils.equals(message.getPath(), Paths.RESULT_BUS_STOP_DEPARTURES))
        {
            BusStopDepartureList busStopDepartureList = JsonUtils.fromJson(message.getPayloadAsString(), BusStopDepartureList.class);

            Intent intent = BusStopDepartureListActivity.getInstance(this, busStopDepartureList);
            startActivity(intent);
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
        wearableConnectivity.sendMessage(Calls.getBusStopDepartures(nodeId, busStop.getCode()));
    }

    @Override
    public void onConnectedSuccess()
    {
        wearableConnectivity.getDefaultDeviceNode(new OnDeviceNodeDetected()
        {
            @Override
            public void onDefaultDeviceNode(String nodeId)
            {
                BusStopListActivity.this.onDefaultDeviceNode(nodeId);
            }
        });
    }

    private void onDefaultDeviceNode(String nodeId)
    {
        this.nodeId = nodeId;

        requestFavoriteStops();
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
                Toast.makeText(BusStopListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}