package com.mauriciotogneri.tpgwear.ui.departures;

import com.mauriciotogneri.common.api.tpg.json.Departure;

public interface DeparturesViewObserver
{
    void onStubReady();

    void onDepartureSelected(Departure departure);
}