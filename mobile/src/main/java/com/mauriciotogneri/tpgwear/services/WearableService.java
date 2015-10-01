package com.mauriciotogneri.tpgwear.services;

import android.text.TextUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.mauriciotogneri.common.api.tpg.GetLinesColors;
import com.mauriciotogneri.common.api.tpg.GetNextDepartures;
import com.mauriciotogneri.common.api.tpg.GetThermometer;
import com.mauriciotogneri.common.api.tpg.Stop;
import com.mauriciotogneri.common.api.message.Message;
import com.mauriciotogneri.common.api.message.MessageApi.Messages;
import com.mauriciotogneri.common.api.message.MessageApi.Paths;
import com.mauriciotogneri.common.utils.EncodingHelper;
import com.mauriciotogneri.tpgwear.utils.Preferences;
import com.mauriciotogneri.tpgwear.api.TpgApi;
import com.mauriciotogneri.tpgwear.api.TpgApi.OnRequestResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WearableService extends WearableListenerService
{
    private static final int TIMEOUT = 1000 * 10; // in milliseconds

    @Override
    public void onMessageReceived(MessageEvent messageEvent)
    {
        String nodeId = messageEvent.getSourceNodeId();
        String path = messageEvent.getPath();
        String payload = EncodingHelper.getBytesAsString(messageEvent.getData());

        if (TextUtils.equals(path, Paths.GET_FAVORITE_STOPS))
        {
            getFavoriteStops(nodeId);
        }
        else if (TextUtils.equals(path, Paths.GET_DEPARTURES))
        {
            getDepartures(nodeId, payload);
        }
        else if (TextUtils.equals(path, Paths.GET_TRIP))
        {
            getTrip(nodeId, payload);
        }
        else if (TextUtils.equals(path, Paths.INCREASE_STOP_HIT_COUNT))
        {
            increaseStopHitCount(payload);
        }
    }

    private void getFavoriteStops(String nodeId)
    {
        Preferences preferences = Preferences.getInstance(this);
        List<Stop> stops = preferences.getFavoriteStops();

        reply(Messages.resultFavoriteStops(nodeId, stops));
    }

    private void getDepartures(final String nodeId, final String stopCode)
    {
        final TpgApi tpgApi = TpgApi.getInstance(this);

        tpgApi.getLinesColors(new OnRequestResult<GetLinesColors>()
        {
            @Override
            public void onSuccess(final GetLinesColors linesColors)
            {
                tpgApi.getNextDepartures(stopCode, new OnRequestResult<GetNextDepartures>()
                {
                    @Override
                    public void onSuccess(GetNextDepartures nextDepartures)
                    {
                        nextDepartures.setColors(linesColors);
                        nextDepartures.removeInvalidDepartures();

                        reply(Messages.resultDepartures(nodeId, nextDepartures.departures));
                    }

                    @Override
                    public void onFailure()
                    {
                    }
                });
            }

            @Override
            public void onFailure()
            {
            }
        });
    }

    private void getTrip(final String nodeId, final String departureCode)
    {
        final TpgApi tpgApi = TpgApi.getInstance(this);

        tpgApi.getThermometer(Integer.parseInt(departureCode), new OnRequestResult<GetThermometer>()
        {
            @Override
            public void onSuccess(GetThermometer trip)
            {
                reply(Messages.resultTrip(nodeId, trip.steps));
            }

            @Override
            public void onFailure()
            {
            }
        });
    }

    private void increaseStopHitCount(String stopCode)
    {
        Preferences preferences = Preferences.getInstance(this);
        preferences.increaseHitCount(stopCode);
    }

    private void reply(final Message message)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                GoogleApiClient client = new GoogleApiClient.Builder(WearableService.this).addApi(Wearable.API).build();
                ConnectionResult connectionResult = client.blockingConnect(TIMEOUT, TimeUnit.MILLISECONDS);

                if (connectionResult.isSuccess())
                {
                    Wearable.MessageApi.sendMessage(client, message.getNodeId(), message.getPath(), message.getPayloadAsBytes());
                }

                client.disconnect();
            }
        }).start();
    }
}