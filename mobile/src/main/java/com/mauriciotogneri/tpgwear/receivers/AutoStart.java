package com.mauriciotogneri.tpgwear.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mauriciotogneri.tpgwear.services.WearableService;

public class AutoStart extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent intentService = new Intent(context, WearableService.class);
        context.startService(intentService);
    }
}