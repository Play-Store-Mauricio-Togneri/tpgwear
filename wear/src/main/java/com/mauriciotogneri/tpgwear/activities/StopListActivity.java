package com.mauriciotogneri.tpgwear.activities;

import android.content.Intent;
import android.text.TextUtils;

import com.mauriciotogneri.common.api.wearable.Message;
import com.mauriciotogneri.common.api.wearable.WearableApi.Messages;
import com.mauriciotogneri.common.api.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.OnDeviceNodeDetected;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.model.BusStop;
import com.mauriciotogneri.common.model.BusStopList;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.mauriciotogneri.tpgwear.ui.busstoplist.BusStopListInterface;
import com.mauriciotogneri.tpgwear.ui.busstoplist.BusStopListObserver;
import com.mauriciotogneri.tpgwear.ui.busstoplist.BusStopListView;

public class StopListActivity extends BaseActivity<BusStopListInterface> implements WearableEvents, BusStopListObserver
{
    private String nodeId = "";
    private WearableConnectivity connectivity;

    @Override
    protected void initialize()
    {
        view.initialize(this);
    }

    @Override
    public void onStubReady()
    {
        connectivity = new WearableConnectivity(this, this);
        connectivity.connect();
    }

    @Override
    public void onConnectedSuccess()
    {
        connectivity.getDefaultDeviceNode(new OnDeviceNodeDetected()
        {
            @Override
            public void onDefaultDeviceNode(String deviceNodeId)
            {
                nodeId = deviceNodeId;

                connectivity.sendMessage(Messages.getFavoriteBusStops(nodeId));
            }
        });
    }

    @Override
    public void onMessageReceived(Message message)
    {
        if (TextUtils.equals(message.getPath(), Paths.RESULT_FAVORITE_BUS_STOPS))
        {
            BusStopList busStopList = JsonUtils.fromJson(message.getPayloadAsString(), BusStopList.class);
            view.displayData(busStopList);
        }
    }

    @Override
    public void onBusStopSelected(BusStop busStop)
    {
        Intent intent = StopDepartureListActivity.getInstance(this, nodeId, busStop.getCode());
        startActivity(intent);
    }

    @Override
    public void onConnectedFail()
    {
        toast("CLIENT DISCONNECTED");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (connectivity != null)
        {
            connectivity.disconnect();
        }
    }

    @Override
    protected BusStopListInterface getViewInstance()
    {
        return new BusStopListView();
    }
}