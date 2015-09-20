package com.mauriciotogneri.tpgwear.ui.favorites;

import com.mauriciotogneri.common.api.tpg.json.Stop;

public interface FavoriteStopsViewObserver
{
    void onAddFavorites();

    void onEditFavorites();

    void onStopSelected(Stop stop);
}