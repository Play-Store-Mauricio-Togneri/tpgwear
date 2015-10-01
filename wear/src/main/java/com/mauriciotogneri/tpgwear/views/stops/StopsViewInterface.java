package com.mauriciotogneri.tpgwear.views.stops;

import com.mauriciotogneri.common.api.tpg.Stop;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;

import java.util.List;

public interface StopsViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(StopsViewObserver observer);

    void displayData(List<Stop> stops);
}