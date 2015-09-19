package com.mauriciotogneri.tpgwear.ui.busstoplist;

import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;
import com.mauriciotogneri.common.model.BusStopList;

public interface BusStopListInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(BusStopListObserver observer);

    void displayData(BusStopList busStopList);
}