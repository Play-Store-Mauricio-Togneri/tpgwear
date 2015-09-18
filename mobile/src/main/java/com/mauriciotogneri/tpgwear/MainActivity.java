package com.mauriciotogneri.tpgwear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.mauriciotogneri.common.api.tpg.TpgApi;
import com.mauriciotogneri.common.api.tpg.TpgApi.OnRequestResult;
import com.mauriciotogneri.common.api.tpg.json.GetDisruptions;
import com.mauriciotogneri.common.api.tpg.json.GetLinesColors;
import com.mauriciotogneri.common.api.tpg.json.GetNextDepartures;
import com.mauriciotogneri.common.api.tpg.json.GetStops;
import com.mauriciotogneri.common.api.tpg.json.GetThermometer;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, WearableService.class);
        startService(intent);

        TpgApi tpgApi = TpgApi.getInstance(this);

        tpgApi.getStops(new OnRequestResult<GetStops>()
        {
            @Override
            public void onSuccess(GetStops result)
            {
                System.out.println();
            }

            @Override
            public void onFailure()
            {
                System.out.println();
            }
        });

        tpgApi.getLinesColors(new OnRequestResult<GetLinesColors>()
        {
            @Override
            public void onSuccess(GetLinesColors result)
            {
                System.out.println();
            }

            @Override
            public void onFailure()
            {
                System.out.println();
            }
        });

        tpgApi.getNextDepartures("CMAR", new OnRequestResult<GetNextDepartures>()
        {
            @Override
            public void onSuccess(GetNextDepartures result)
            {
                System.out.println();
            }

            @Override
            public void onFailure()
            {
                System.out.println();
            }
        });

        tpgApi.getThermometer(153882, new OnRequestResult<GetThermometer>()
        {
            @Override
            public void onSuccess(GetThermometer result)
            {
                System.out.println();
            }

            @Override
            public void onFailure()
            {
                System.out.println();
            }
        });

        tpgApi.getDisruptions(new OnRequestResult<GetDisruptions>()
        {
            @Override
            public void onSuccess(GetDisruptions result)
            {
                System.out.println();
            }

            @Override
            public void onFailure()
            {
                System.out.println();
            }
        });
    }
}