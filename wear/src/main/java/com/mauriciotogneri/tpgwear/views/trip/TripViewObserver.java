package com.mauriciotogneri.tpgwear.views.trip;

import com.mauriciotogneri.common.api.tpg.json.Step;

public interface TripViewObserver
{
    void onStubReady();

    void onStepSelected(Step step);
}