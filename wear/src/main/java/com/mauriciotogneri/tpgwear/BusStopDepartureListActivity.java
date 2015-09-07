package com.mauriciotogneri.tpgwear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.View;

import com.mauriciotogneri.common.model.BusStopDepartureList;
import com.mauriciotogneri.tpgwear.adapters.BusStopDepartureAdapter;

public class BusStopDepartureListActivity extends Activity
{
    private static final String EXTRA_DEPARTURES = "EXTRA_DEPARTURES";

    public static Intent getInstance(Context context, BusStopDepartureList busStopDepartureList)
    {
        Intent intent = new Intent(context, BusStopDepartureListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_DEPARTURES, busStopDepartureList.toString());

        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stub_bus_stop_departure);

        WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener()
        {
            @Override
            public void onLayoutInflated(WatchViewStub stub)
            {
                String extra = getIntent().getStringExtra(EXTRA_DEPARTURES);
                BusStopDepartureList busStopDepartureList = BusStopDepartureList.fromString(extra);

                displayDepartures(busStopDepartureList);
            }
        });
    }

    private void displayDepartures(BusStopDepartureList busStopDepartureList)
    {
        WearableListView wearableListView = (WearableListView) findViewById(R.id.bus_stop_departures_list);
        wearableListView.setAdapter(new BusStopDepartureAdapter(this, busStopDepartureList));
        wearableListView.setVisibility(View.VISIBLE);
    }
}