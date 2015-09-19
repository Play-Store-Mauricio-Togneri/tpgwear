package com.mauriciotogneri.tpgwear.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mauriciotogneri.common.api.tpg.json.Departure;
import com.mauriciotogneri.common.api.wearable.Message;
import com.mauriciotogneri.common.api.wearable.WearableApi.Messages;
import com.mauriciotogneri.common.api.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.model.BusStopDeparture;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.mauriciotogneri.tpgwear.ui.busstopdepartures.BusStopDeparturesInterface;
import com.mauriciotogneri.tpgwear.ui.busstopdepartures.BusStopDeparturesObserver;
import com.mauriciotogneri.tpgwear.ui.busstopdepartures.BusStopDeparturesView;

import java.lang.reflect.Type;
import java.util.List;

public class StopDepartureListActivity extends BaseActivity<BusStopDeparturesInterface> implements WearableEvents, BusStopDeparturesObserver
{
    private WearableConnectivity connectivity;

    private static final String EXTRA_NODE_ID = "EXTRA_NODE_ID";
    private static final String EXTRA_BUS_STOP_CODE = "EXTRA_BUS_STOP_CODE";

    public static Intent getInstance(Context context, String nodeId, String busStopCode)
    {
        Intent intent = new Intent(context, StopDepartureListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NODE_ID, nodeId);
        bundle.putString(EXTRA_BUS_STOP_CODE, busStopCode);

        intent.putExtras(bundle);

        return intent;
    }

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
        String nodeId = getIntent().getStringExtra(EXTRA_NODE_ID);
        String busStopCode = getIntent().getStringExtra(EXTRA_BUS_STOP_CODE);

        connectivity.sendMessage(Messages.getBusStopDepartures(nodeId, busStopCode));
    }

    @Override
    public void onMessageReceived(Message message)
    {
        if (TextUtils.equals(message.getPath(), Paths.RESULT_BUS_STOP_DEPARTURES))
        {
            Type listType = new TypeToken<List<Departure>>()
            {
            }.getType();

            List<Departure> departures = JsonUtils.fromJson(message.getPayloadAsString(), listType);
            view.displayData(departures);
        }
    }

    @Override
    public void onBusStopDepartureSelected(BusStopDeparture busStopDeparture)
    {
        toast("SELECTED!");
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
    protected BusStopDeparturesInterface getViewInstance()
    {
        return new BusStopDeparturesView();
    }
}