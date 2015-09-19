package com.mauriciotogneri.tpgwear.ui.busstopdepartures;

import com.mauriciotogneri.common.model.BusStopDeparture;

public interface BusStopDeparturesObserver
{
    void onStubReady();

    void onBusStopDepartureSelected(BusStopDeparture busStopDeparture);
}