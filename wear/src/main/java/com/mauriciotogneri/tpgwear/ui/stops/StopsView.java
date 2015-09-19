package com.mauriciotogneri.tpgwear.ui.stops;

import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WatchViewStub.OnLayoutInflatedListener;
import android.support.wearable.view.WearableListView;
import android.support.wearable.view.WearableListView.ClickListener;
import android.support.wearable.view.WearableListView.OnScrollListener;
import android.support.wearable.view.WearableListView.ViewHolder;
import android.view.View;
import android.widget.ProgressBar;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StopAdapter;
import com.mauriciotogneri.tpgwear.adapters.StopAdapter.StopViewHolder;
import com.mauriciotogneri.tpgwear.ui.stops.StopsView.UiContainer;

import java.util.List;

public class StopsView extends BaseView<UiContainer> implements StopsInterface<UiContainer>
{
    private StopAdapter adapter;

    @Override
    public void initialize(final StopsObserver observer)
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

    private void onLoad(final StopsObserver observer)
    {
        ui.progressBar.setVisibility(View.VISIBLE);

        adapter = new StopAdapter(getContext());

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
                StopViewHolder stopViewHolder = (StopViewHolder) viewHolder;
                observer.onStopSelected(stopViewHolder.getStop());
            }

            @Override
            public void onTopEmptyRegionClick()
            {
            }
        });

        observer.onStubReady();
    }

    @Override
    public void displayData(List<Stop> stops)
    {
        ui.progressBar.setVisibility(View.GONE);
        ui.list.setVisibility(View.VISIBLE);

        adapter.setData(stops);
    }

    @Override
    public int getViewId()
    {
        return R.layout.stub_stops;
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
        private View header;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        }

        public void load()
        {
            this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            this.list = (WearableListView) findViewById(R.id.list);
            this.header = findViewById(R.id.header);
        }
    }
}