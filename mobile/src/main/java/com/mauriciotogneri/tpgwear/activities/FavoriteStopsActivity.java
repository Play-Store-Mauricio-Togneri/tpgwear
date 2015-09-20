package com.mauriciotogneri.tpgwear.activities;

import android.content.Intent;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.utils.Preferences;
import com.mauriciotogneri.tpgwear.services.WearableService;
import com.mauriciotogneri.tpgwear.ui.FavoriteStopsView;
import com.mauriciotogneri.tpgwear.ui.FavoriteStopsViewInterface;
import com.mauriciotogneri.tpgwear.ui.FavoriteStopsViewObserver;

import java.util.ArrayList;
import java.util.List;

public class FavoriteStopsActivity extends BaseActivity<FavoriteStopsViewInterface> implements FavoriteStopsViewObserver
{
    @Override
    protected void initialize()
    {
        view.initialize(this);

        Intent intent = new Intent(this, WearableService.class);
        startService(intent);

        Preferences preferences = new Preferences(this);
        preferences.getFavoriteStops();

        List<Stop> stops = getDefaultStopList();
        view.displayData(stops);
    }

    private List<Stop> getDefaultStopList()
    {
        Stop stopCamilleMartin = new Stop();
        stopCamilleMartin.stopCode = "CMAR";
        stopCamilleMartin.stopName = "Camille-Martin";

        Stop stopEpinettes = new Stop();
        stopEpinettes.stopCode = "EPIN";
        stopEpinettes.stopName = "Epinettes";

        Stop stopMileant = new Stop();
        stopMileant.stopCode = "MILE";
        stopMileant.stopName = "Miléant";

        Stop stopBelAir = new Stop();
        stopBelAir.stopCode = "BAIR";
        stopBelAir.stopName = "Bel-Air";

        Stop stopBains = new Stop();
        stopBains.stopCode = "BANS";
        stopBains.stopName = "Bains";

        Stop stopPalettes = new Stop();
        stopPalettes.stopCode = "PALE";
        stopPalettes.stopName = "Palettes";

        List<Stop> stops = new ArrayList<>();
        stops.add(stopCamilleMartin);
        stops.add(stopEpinettes);
        stops.add(stopMileant);
        stops.add(stopBelAir);
        stops.add(stopBains);
        stops.add(stopPalettes);

        return stops;
    }

    @Override
    protected FavoriteStopsViewInterface getViewInstance()
    {
        return new FavoriteStopsView();
    }

    @Override
    public void onStopSelected(Stop stop)
    {
        view.toast("SELECTED!");
    }

    @Override
    public void onAddFavorites()
    {
        view.toast("ADD!");
    }
}