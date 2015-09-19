package com.mauriciotogneri.tpgwear.ui.trip;

import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WatchViewStub.OnLayoutInflatedListener;
import android.support.wearable.view.WearableListView;
import android.support.wearable.view.WearableListView.ClickListener;
import android.support.wearable.view.WearableListView.OnScrollListener;
import android.support.wearable.view.WearableListView.ViewHolder;
import android.view.View;
import android.widget.ProgressBar;

import com.mauriciotogneri.common.api.tpg.json.Step;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StepAdapter;
import com.mauriciotogneri.tpgwear.adapters.StepAdapter.StepViewHolder;
import com.mauriciotogneri.tpgwear.ui.trip.TripView.UiContainer;

import java.util.List;

public class TripView extends BaseView<UiContainer> implements TripViewInterface<UiContainer>
{
    private StepAdapter adapter;

    @Override
    public void initialize(final TripViewObserver observer)
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

    private void onLoad(final TripViewObserver observer)
    {
        ui.progressBar.setVisibility(View.VISIBLE);

        adapter = new StepAdapter(getContext());

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
                StepViewHolder stepViewHolder = (StepViewHolder) viewHolder;
                observer.onStepSelected(stepViewHolder.get());
            }

            @Override
            public void onTopEmptyRegionClick()
            {
            }
        });

        observer.onStubReady();
    }

    @Override
    public void displayData(List<Step> steps, int position)
    {
        ui.progressBar.setVisibility(View.GONE);
        ui.list.setVisibility(View.VISIBLE);

        adapter.setData(steps);

        if (position > -1)
        {
            ui.list.scrollToPosition(position);
        }
    }

    @Override
    public int getViewId()
    {
        return R.layout.stub_trip;
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