package com.mauriciotogneri.tpgwear.activities;

import android.content.Intent;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseActivity;
import com.mauriciotogneri.common.utils.Preferences;
import com.mauriciotogneri.tpgwear.services.WearableService;
import com.mauriciotogneri.tpgwear.views.favorites.FavoriteStopsView;
import com.mauriciotogneri.tpgwear.views.favorites.FavoriteStopsViewInterface;
import com.mauriciotogneri.tpgwear.views.favorites.FavoriteStopsViewObserver;

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
    public void onAddFavorites()
    {
        Intent intent = new Intent(this, StopCatalogueActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEditFavorites()
    {
        view.toggleEdit();
    }

    @Override
    public void onStopSelected(Stop stop)
    {
        Preferences preferences = Preferences.getInstance(this);
        List<Stop> stops = preferences.removeFavoriteStop(stop);

        view.displayData(stops);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        view.disableEdit();
        displayStops();
    }

    @Override
    protected FavoriteStopsViewInterface getViewInstance()
    {
        return new FavoriteStopsView();
    }
}