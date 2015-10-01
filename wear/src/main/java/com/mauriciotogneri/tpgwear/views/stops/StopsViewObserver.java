package com.mauriciotogneri.tpgwear.views.stops;

import com.mauriciotogneri.common.api.tpg.Stop;

public interface StopsViewObserver
{
    void onStubReady();

    void onStopSelected(Stop stop);
}