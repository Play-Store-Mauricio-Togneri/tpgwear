package com.mauriciotogneri.tpgwear.views.trip;

import com.mauriciotogneri.common.api.tpg.json.Step;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseViewInterface;

import java.util.List;

public interface TripViewInterface<UI extends BaseUiContainer> extends BaseViewInterface<UI>
{
    void initialize(TripViewObserver observer);

    void displayData(List<Step> steps, int position);
}