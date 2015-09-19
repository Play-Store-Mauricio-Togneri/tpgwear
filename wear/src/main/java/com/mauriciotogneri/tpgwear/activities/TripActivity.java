package com.mauriciotogneri.tpgwear.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mauriciotogneri.common.api.tpg.json.Step;
import com.mauriciotogneri.common.api.wearable.Message;
import com.mauriciotogneri.common.api.wearable.WearableApi.Messages;
import com.mauriciotogneri.common.api.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.mauriciotogneri.tpgwear.ui.trip.TripView;
import com.mauriciotogneri.tpgwear.ui.trip.TripViewInterface;
import com.mauriciotogneri.tpgwear.ui.trip.TripViewObserver;

import java.lang.reflect.Type;
import java.util.List;

public class TripActivity extends BaseActivity<TripViewInterface> implements WearableEvents, TripViewObserver
{
    private WearableConnectivity connectivity;

    private static final String EXTRA_NODE_ID = "EXTRA_NODE_ID";
    private static final String EXTRA_DEPARTURE_CODE = "EXTRA_DEPARTURE_CODE";

    public static Intent getInstance(Context context, String nodeId, String departureCode)
    {
        Intent intent = new Intent(context, TripActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NODE_ID, nodeId);
        bundle.putString(EXTRA_DEPARTURE_CODE, departureCode);

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
        String departureCode = getIntent().getStringExtra(EXTRA_DEPARTURE_CODE);

        connectivity.sendMessage(Messages.getTrip(nodeId, departureCode));
    }

    @Override
    public void onMessageReceived(Message message)
    {
        if (TextUtils.equals(message.getPath(), Paths.RESULT_TRIP))
        {
            Type type = new TypeToken<List<Step>>()
            {
            }.getType();

            String departureCode = getIntent().getStringExtra(EXTRA_DEPARTURE_CODE);
            List<Step> steps = JsonUtils.fromJson(message.getPayloadAsString(), type);

            int position = getPositionInList(steps, Integer.parseInt(departureCode));
            view.displayData(steps, position);
        }
    }

    private int getPositionInList(List<Step> steps, int departureCode)
    {
        for (int i = 0; i < steps.size(); i++)
        {
            Step step = steps.get(i);

            if (step.departureCode == departureCode)
            {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void onStepSelected(Step step)
    {
        String nodeId = getIntent().getStringExtra(EXTRA_NODE_ID);

        Intent intent = DeparturesActivity.getInstance(this, nodeId, step.stop.stopCode);
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
    protected TripViewInterface getViewInstance()
    {
        return new TripView();
    }
}