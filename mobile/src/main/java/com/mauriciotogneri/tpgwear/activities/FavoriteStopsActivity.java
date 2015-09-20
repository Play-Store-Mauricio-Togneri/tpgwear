package com.mauriciotogneri.tpgwear.activities;

import android.content.Intent;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.utils.Preferences;
import com.mauriciotogneri.tpgwear.services.WearableService;
import com.mauriciotogneri.tpgwear.ui.favorites.FavoriteStopsView;
import com.mauriciotogneri.tpgwear.ui.favorites.FavoriteStopsViewInterface;
import com.mauriciotogneri.tpgwear.ui.favorites.FavoriteStopsViewObserver;

import java.util.List;

public class FavoriteStopsActivity extends BaseActivity<FavoriteStopsViewInterface> implements FavoriteStopsViewObserver
{
    @Override
    protected void initialize()
    {
        view.initialize(this);

        Intent intent = new Intent(this, WearableService.class);
        startService(intent);

        displayStops();
    }

    private void displayStops()
    {
        Preferences preferences = Preferences.getInstance(this);
        List<Stop> stops = preferences.getFavoriteStops();

        view.displayData(stops);
    }

    @Override
    public void onStopSelected(Stop stop)
    {
        view.toast("SELECTED!");

        Preferences preferences = Preferences.getInstance(this);
        preferences.increaseHitCount(stop.stopCode);
    }

    @Override
    public void onAddFavorites()
    {
        Intent intent = new Intent(this, StopCatalogueActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEditFavorites()
    {
        toast("EDIT FAVORITES!");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        displayStops();
    }

    @Override
    protected FavoriteStopsViewInterface getViewInstance()
    {
        return new FavoriteStopsView();
    }
}