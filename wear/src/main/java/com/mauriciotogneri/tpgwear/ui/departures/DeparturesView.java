package com.mauriciotogneri.tpgwear.ui.departures;

import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WatchViewStub.OnLayoutInflatedListener;
import android.support.wearable.view.WearableListView;
import android.support.wearable.view.WearableListView.ClickListener;
import android.support.wearable.view.WearableListView.OnScrollListener;
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

public class DeparturesView extends BaseView<UiContainer> implements DeparturesViewInterface<UiContainer>
{
    private DepartureAdapter adapter;

    @Override
    public void initialize(final DeparturesViewObserver observer)
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

    private void onLoad(final DeparturesViewObserver observer)
    {
        ui.progressBar.setVisibility(View.VISIBLE);
        ui.content.setVisibility(View.GONE);

        adapter = new DepartureAdapter(getContext());

        ui.list.setAdapter(adapter);
        ui.list.addOnScrollListener(new OnScrollListener()
        {
            @Override
            public void onAbsoluteScrollChange(int i)
            {
                if (i > 0)
                {
                    ui.header.setY(-i);
                }
            }

            @Override
            public void onScroll(int i)
            {
            }

            @Override
            public void onScrollStateChanged(int i)
            {
            }

            @Override
            public void onCentralPositionChanged(int i)
            {
            }
        });
        ui.list.setClickListener(new ClickListener()
        {
            @Override
            public void onClick(ViewHolder viewHolder)
            {
                DepartureViewHolder departureViewHolder = (DepartureViewHolder) viewHolder;
                observer.onDepartureSelected(departureViewHolder.get());
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
        ui.content.setVisibility(View.VISIBLE);

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
        private View content;
        private ProgressBar progressBar;
        private WearableListView list;
        private View header;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        }

        public void load()
        {
            this.content = findViewById(R.id.content);
            this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            this.list = (WearableListView) findViewById(R.id.list);
            this.header = findViewById(R.id.header);
        }
    }
}