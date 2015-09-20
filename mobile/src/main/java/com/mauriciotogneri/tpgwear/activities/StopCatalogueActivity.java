package com.mauriciotogneri.tpgwear.activities;

import com.mauriciotogneri.common.api.tpg.TpgApi;
import com.mauriciotogneri.common.api.tpg.TpgApi.OnRequestResult;
import com.mauriciotogneri.common.api.tpg.json.GetStops;
import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.utils.Preferences;
import com.mauriciotogneri.tpgwear.ui.catalogue.StopCatalogueView;
import com.mauriciotogneri.tpgwear.ui.catalogue.StopCatalogueViewInterface;
import com.mauriciotogneri.tpgwear.ui.catalogue.StopCatalogueViewObserver;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StopCatalogueActivity extends BaseActivity<StopCatalogueViewInterface> implements StopCatalogueViewObserver
{
    @Override
    protected void initialize()
    {
        view.initialize(this);

        TpgApi tpgApi = TpgApi.getInstance(this);
        tpgApi.getStops(new OnRequestResult<GetStops>()
        {
            @Override
            public void onSuccess(GetStops stops)
            {
                onStopLoaded(stops.stops);
            }

            @Override
            public void onFailure()
            {
                view.hideLoading();
                view.toast("CONNECTION ERROR");
            }
        });
    }

    private void onStopLoaded(List<Stop> stops)
    {
        Preferences preferences = Preferences.getInstance(this);
        List<Stop> favoriteStops = preferences.getFavoriteStops();

        for (Stop stop : stops)
        {
            stop.favorite = favoriteStops.contains(stop);
        }

        Collections.sort(stops, new Comparator<Stop>()
        {
            @Override
            public int compare(Stop lhs, Stop rhs)
            {
                return lhs.stopName.compareToIgnoreCase(rhs.stopName);
            }
        });

        view.displayData(stops);
    }

    @Override
    public void onStopSelected(Stop stop)
    {
        stop.favorite = !stop.favorite;

        Preferences preferences = Preferences.getInstance(this);

        if (stop.favorite)
        {
            preferences.addFavoriteStop(stop);
        }
        else
        {
            preferences.removeFavoriteStop(stop);
        }

        view.refreshData();
    }

    @Override
    public void onSearchStop()
    {
        toast("SEARCH STOP!");
    }

    @Override
    protected StopCatalogueViewInterface getViewInstance()
    {
        return new StopCatalogueView();
    }
}