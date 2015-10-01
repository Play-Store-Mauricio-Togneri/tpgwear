package com.mauriciotogneri.tpgwear.views.catalogue;

import com.mauriciotogneri.common.api.tpg.Stop;

public interface StopCatalogueViewObserver
{
    void onStopSelected(Stop stop);

    void onSearchStop();
}