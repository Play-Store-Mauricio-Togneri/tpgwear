package com.mauriciotogneri.tpgwear.ui.busstoplist;

import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WatchViewStub.OnLayoutInflatedListener;
import android.support.wearable.view.WearableListView;
import android.support.wearable.view.WearableListView.ClickListener;
import android.support.wearable.view.WearableListView.ViewHolder;
import android.view.View;
import android.widget.ProgressBar;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.common.model.BusStopList;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.BusStopAdapter;
import com.mauriciotogneri.tpgwear.adapters.BusStopAdapter.BusStopViewHolder;
import com.mauriciotogneri.tpgwear.ui.busstoplist.BusStopListView.UiContainer;

public class BusStopListView extends BaseView<UiContainer> implements BusStopListInterface<UiContainer>
{
    private BusStopAdapter busStopAdapter;

    @Override
    public void initialize(final BusStopListObserver observer)
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

    private void onLoad(final BusStopListObserver observer)
    {
        ui.progressBar.setVisibility(View.VISIBLE);

        busStopAdapter = new BusStopAdapter(getContext());

        ui.list.setAdapter(busStopAdapter);
        ui.list.setClickListener(new ClickListener()
        {
            @Override
            public void onClick(ViewHolder viewHolder)
            {
                BusStopViewHolder busStopViewHolder = (BusStopViewHolder) viewHolder;
                observer.onBusStopSelected(busStopViewHolder.getBusStop());
            }

            @Override
            public void onTopEmptyRegionClick()
            {
            }
        });

        observer.onStubReady();
    }

    @Override
    public void displayData(BusStopList busStopList)
    {
        ui.progressBar.setVisibility(View.GONE);
        ui.list.setVisibility(View.VISIBLE);

        busStopAdapter.setData(busStopList);
    }

    @Override
    public int getViewId()
    {
        return R.layout.stub_bus_stop_list;
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
            this.list = (WearableListView) findViewById(R.id.bus_stop_list);
        }
    }
}