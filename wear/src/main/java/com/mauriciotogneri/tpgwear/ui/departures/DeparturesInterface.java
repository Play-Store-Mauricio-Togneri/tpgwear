package com.mauriciotogneri.tpgwear.ui.departures;

import com.mauriciotogneri.common.api.tpg.json.Departure;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;

import java.util.List;

public interface DeparturesInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(DeparturesObserver observer);

    void displayData(List<Departure> departures);
}