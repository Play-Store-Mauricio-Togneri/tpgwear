package com.mauriciotogneri.tpgwear.ui;

import com.mauriciotogneri.common.api.tpg.json.Stop;

public interface FavoriteStopsViewObserver
{
    void onStopSelected(Stop stop);

    void onAddFavorites();
}