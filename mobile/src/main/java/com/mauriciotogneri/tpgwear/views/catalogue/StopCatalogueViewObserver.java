package com.mauriciotogneri.tpgwear.views.catalogue;

import com.mauriciotogneri.common.api.tpg.json.Stop;

public interface StopCatalogueViewObserver
{
    void onStopSelected(Stop stop);

    void onSearchStop();
}