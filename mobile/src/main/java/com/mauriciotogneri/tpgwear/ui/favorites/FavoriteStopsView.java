package com.mauriciotogneri.tpgwear.ui.favorites;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.FavoriteStopAdapter;
import com.mauriciotogneri.tpgwear.ui.favorites.FavoriteStopsView.UiContainer;

import java.util.List;

public class FavoriteStopsView extends BaseView<UiContainer> implements FavoriteStopsViewInterface<UiContainer>
{
    private FavoriteStopAdapter adapter;

    @Override
    public void initialize(final FavoriteStopsViewObserver observer)
    {
        adapter = new FavoriteStopAdapter(getContext());

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (adapter.isEditEnabled())
                {
                    Stop stop = (Stop) parent.getItemAtPosition(position);
                    observer.onStopSelected(stop);
                }
            }
        });

        ui.buttonAdd.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onAddFavorites();
            }
        });

        ui.buttonEdit.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onEditFavorites();
            }
        });
    }

    @Override
    public void toggleEdit()
    {
        adapter.toggleEdit();
    }

    @Override
    public void disableEdit()
    {
        adapter.disableEdit();
    }

    @Override
    public void displayData(List<Stop> stops)
    {
        adapter.setData(stops);
    }

    @Override
    public int getViewId()
    {
        return R.layout.screen_favorite_stops;
    }

    @Override
    public UiContainer getUiContainer(BaseView baseView)
    {
        return new UiContainer(baseView);
    }

    public static class UiContainer extends BaseUiContainer
    {
        private final ListView list;
        private final FloatingActionButton buttonAdd;
        private final TextView buttonEdit;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.list = (ListView) findViewById(R.id.list);
            this.buttonAdd = (FloatingActionButton) findViewById(R.id.add_favorites);
            this.buttonEdit = (TextView) findViewById(R.id.edit_favorites);
        }
    }
}