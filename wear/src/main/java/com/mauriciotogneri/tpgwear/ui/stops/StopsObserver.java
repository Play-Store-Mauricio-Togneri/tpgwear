package com.mauriciotogneri.tpgwear.ui.stops;

import com.mauriciotogneri.common.api.tpg.json.Stop;

public interface StopsObserver
{
    void onStubReady();

    void onStopSelected(Stop stop);
}