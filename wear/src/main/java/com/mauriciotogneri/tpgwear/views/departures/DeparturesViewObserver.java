package com.mauriciotogneri.tpgwear.views.departures;

import com.mauriciotogneri.common.api.tpg.Departure;

public interface DeparturesViewObserver
{
    void onStubReady();

    void onDepartureSelected(Departure departure);
}