package com.mauriciotogneri.tpgwear.ui.favorites;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;

import java.util.List;

public interface FavoriteStopsViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(FavoriteStopsViewObserver observer);

    void displayData(List<Stop> stops);
}