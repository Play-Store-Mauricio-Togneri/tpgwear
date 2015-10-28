package com.mauriciotogneri.tpgwear.activities;

import com.mauriciotogneri.common.api.tpg.GetLinesColors;
import com.mauriciotogneri.common.api.tpg.GetStops;
import com.mauriciotogneri.common.api.tpg.Stop;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.api.TpgApi;
import com.mauriciotogneri.tpgwear.api.TpgApi.OnRequestResult;
import com.mauriciotogneri.tpgwear.utils.Preferences;
import com.mauriciotogneri.tpgwear.views.catalogue.StopCatalogueView;
import com.mauriciotogneri.tpgwear.views.catalogue.StopCatalogueViewInterface;
import com.mauriciotogneri.tpgwear.views.catalogue.StopCatalogueViewObserver;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StopCatalogueActivity extends BaseActivity<StopCatalogueViewInterface> implements StopCatalogueViewObserver
{
    @Override
    protected void initialize()
    {
        view.initialize(this);

        final TpgApi tpgApi = TpgApi.getInstance(this);

        tpgApi.getLinesColors(new OnRequestResult<GetLinesColors>()
        {
            @Override
            public void onSuccess(final GetLinesColors linesColors)
            {
                tpgApi.getStops(new OnRequestResult<GetStops>()
                {
                    @Override
                    public void onSuccess(GetStops stops)
                    {
                        stops.setColors(linesColors);

                        onStopLoaded(stops.stops);
                    }

                    @Override
                    public void onFailure()
                    {
                        view.hideLoading();
                        view.toast(R.string.error_connection);
                    }
                });
            }

            @Override
            public void onFailure()
            {
                view.hideLoading();
                view.toast(R.string.error_connection);
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
        view.toggleSearch();
    }

    @Override
    protected StopCatalogueViewInterface getViewInstance()
    {
        return new StopCatalogueView();
    }
}