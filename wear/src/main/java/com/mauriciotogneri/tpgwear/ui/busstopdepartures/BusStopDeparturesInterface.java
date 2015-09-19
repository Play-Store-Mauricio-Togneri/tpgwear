package com.mauriciotogneri.tpgwear.ui.busstopdepartures;

import com.mauriciotogneri.common.api.tpg.json.Departure;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;

import java.util.List;

public interface BusStopDeparturesInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(BusStopDeparturesObserver observer);

    void displayData(List<Departure> departures);
}