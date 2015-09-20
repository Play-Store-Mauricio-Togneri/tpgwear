package com.mauriciotogneri.tpgwear.activities;

import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.api.wearable.Message;
import com.mauriciotogneri.common.api.wearable.WearableApi.Messages;
import com.mauriciotogneri.common.api.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.OnDeviceNodeDetected;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.mauriciotogneri.tpgwear.ui.stops.StopsView;
import com.mauriciotogneri.tpgwear.ui.stops.StopsViewInterface;
import com.mauriciotogneri.tpgwear.ui.stops.StopsViewObserver;

import java.lang.reflect.Type;
import java.util.List;

public class StopsActivity extends BaseActivity<StopsViewInterface> implements WearableEvents, StopsViewObserver
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

                connectivity.sendMessage(Messages.getFavoriteStops(nodeId));
            }
        });
    }

    @Override
    public void onMessageReceived(Message message)
    {
        if (TextUtils.equals(message.getPath(), Paths.RESULT_FAVORITE_STOPS))
        {
            Type type = new TypeToken<List<Stop>>()
            {
            }.getType();

            List<Stop> stops = JsonUtils.fromJson(message.getPayloadAsString(), type);
            view.displayData(stops);
        }
    }

    @Override
    public void onStopSelected(Stop stop)
    {
        connectivity.sendMessage(Messages.increaseStopHitCount(nodeId, stop.stopCode));

        Intent intent = DeparturesActivity.getInstance(this, nodeId, stop.stopCode);
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
    protected StopsViewInterface getViewInstance()
    {
        return new StopsView();
    }
}