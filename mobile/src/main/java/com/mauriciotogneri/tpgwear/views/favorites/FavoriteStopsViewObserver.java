package com.mauriciotogneri.tpgwear.views.favorites;

import com.mauriciotogneri.common.api.tpg.Stop;

public interface FavoriteStopsViewObserver
{
    void onAddFavorites();

    void onEditFavorites();

    void onStopSelected(Stop stop);
}