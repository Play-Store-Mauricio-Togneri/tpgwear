package com.mauriciotogneri.tpgwear.ui.busstoplist;

import com.mauriciotogneri.common.model.BusStop;

public interface BusStopListObserver
{
    void onStubReady();

    void onBusStopSelected(BusStop busStop);
}