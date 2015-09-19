package com.mauriciotogneri.tpgwear.ui.trip;

import com.mauriciotogneri.common.api.tpg.json.Step;

public interface TripViewObserver
{
    void onStubReady();

    void onStepSelected(Step step);
}