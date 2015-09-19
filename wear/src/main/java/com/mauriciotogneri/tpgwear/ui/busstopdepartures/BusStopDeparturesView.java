package com.mauriciotogneri.tpgwear.ui.busstopdepartures;

import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WatchViewStub.OnLayoutInflatedListener;
import android.support.wearable.view.WearableListView;
import android.support.wearable.view.WearableListView.ClickListener;
import android.support.wearable.view.WearableListView.ViewHolder;
import android.view.View;
import android.widget.ProgressBar;

import com.mauriciotogneri.common.api.tpg.json.Departure;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StopDepartureAdapter;
import com.mauriciotogneri.tpgwear.adapters.StopDepartureAdapter.BusStopDepartureViewHolder;
import com.mauriciotogneri.tpgwear.ui.busstopdepartures.BusStopDeparturesView.UiContainer;

import java.util.List;

public class BusStopDeparturesView extends BaseView<UiContainer> implements BusStopDeparturesInterface<UiContainer>
{
    private StopDepartureAdapter adapter;

    @Override
    public void initialize(final BusStopDeparturesObserver observer)
    {
        ui.stub.setOnLayoutInflatedListener(new OnLayoutInflatedListener()
        {
            @Override
            public void onLayoutInflated(WatchViewStub stub)
            {
                ui.load();

                onLoad(observer);
            }
        });
    }

    private void onLoad(final BusStopDeparturesObserver observer)
    {
        ui.progressBar.setVisibility(View.VISIBLE);

        adapter = new StopDepartureAdapter(getContext());

        ui.list.setAdapter(adapter);
        ui.list.setClickListener(new ClickListener()
        {
            @Override
            public void onClick(ViewHolder viewHolder)
            {
                BusStopDepartureViewHolder busStopViewHolder = (BusStopDepartureViewHolder) viewHolder;
                observer.onBusStopDepartureSelected(busStopViewHolder.getBusStopDeparture());
            }

            @Override
            public void onTopEmptyRegionClick()
            {
            }
        });

        observer.onStubReady();
    }

    @Override
    public void displayData(List<Departure> departures)
    {
        ui.progressBar.setVisibility(View.GONE);
        ui.list.setVisibility(View.VISIBLE);

        adapter.setData(departures);
    }

    @Override
    public int getViewId()
    {
        return R.layout.stub_bus_stop_departure;
    }

    @Override
    public UiContainer getUiContainer(BaseView baseView)
    {
        return new UiContainer(baseView);
    }

    public static class UiContainer extends BaseUiContainer
    {
        private WatchViewStub stub;
        private ProgressBar progressBar;
        private WearableListView list;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        }

        public void load()
        {
            this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            this.list = (WearableListView) findViewById(R.id.bus_stop_departures_list);
        }
    }
}