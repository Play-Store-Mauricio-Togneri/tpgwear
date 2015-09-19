package com.mauriciotogneri.tpgwear.ui.departures;

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
import com.mauriciotogneri.tpgwear.adapters.DepartureAdapter;
import com.mauriciotogneri.tpgwear.adapters.DepartureAdapter.DepartureViewHolder;
import com.mauriciotogneri.tpgwear.ui.departures.DeparturesView.UiContainer;

import java.util.List;

public class DeparturesView extends BaseView<UiContainer> implements DeparturesInterface<UiContainer>
{
    private DepartureAdapter adapter;

    @Override
    public void initialize(final DeparturesObserver observer)
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

    private void onLoad(final DeparturesObserver observer)
    {
        ui.progressBar.setVisibility(View.VISIBLE);

        adapter = new DepartureAdapter(getContext());

        ui.list.setAdapter(adapter);
        ui.list.setClickListener(new ClickListener()
        {
            @Override
            public void onClick(ViewHolder viewHolder)
            {
                DepartureViewHolder departureViewHolder = (DepartureViewHolder) viewHolder;
                observer.onDepartureSelected(departureViewHolder.getDeparture());
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
        return R.layout.stub_departure;
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
            this.list = (WearableListView) findViewById(R.id.list);
        }
    }
}