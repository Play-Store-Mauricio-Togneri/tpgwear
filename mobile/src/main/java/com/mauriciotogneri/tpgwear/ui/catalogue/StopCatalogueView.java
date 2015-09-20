package com.mauriciotogneri.tpgwear.ui.catalogue;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StopCatalogueAdapter;
import com.mauriciotogneri.tpgwear.ui.catalogue.StopCatalogueView.UiContainer;

import java.util.List;

public class StopCatalogueView extends BaseView<UiContainer> implements StopCatalogueViewInterface<UiContainer>
{
    private StopCatalogueAdapter adapter;

    @Override
    public void initialize(final StopCatalogueViewObserver observer)
    {
        adapter = new StopCatalogueAdapter(getContext());

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Stop stop = (Stop) parent.getItemAtPosition(position);
                observer.onStopSelected(stop);
            }
        });
    }

    @Override
    public void displayData(List<Stop> stops)
    {
        adapter.setData(stops);
    }

    @Override
    public void refreshData()
    {
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getViewId()
    {
        return R.layout.screen_stop_catalogue;
    }

    @Override
    public UiContainer getUiContainer(BaseView baseView)
    {
        return new UiContainer(baseView);
    }

    public static class UiContainer extends BaseUiContainer
    {
        private final ListView list;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.list = (ListView) findViewById(R.id.list);
        }
    }
}