package com.mauriciotogneri.tpgwear.views.favorites;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;

import java.util.List;

public interface FavoriteStopsViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(FavoriteStopsViewObserver observer);

    void toggleEdit();

    void disableEdit();

    void displayData(List<Stop> stops);
}