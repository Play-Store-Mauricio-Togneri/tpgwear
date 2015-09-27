package com.mauriciotogneri.tpgwear.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mauriciotogneri.common.api.tpg.json.GetLinesColors;
import com.mauriciotogneri.common.api.tpg.json.GetNextDepartures;
import com.mauriciotogneri.common.api.tpg.json.GetThermometer;
import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.api.wearable.Message;
import com.mauriciotogneri.common.api.wearable.WearableApi.Messages;
import com.mauriciotogneri.common.api.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.common.utils.Preferences;
import com.mauriciotogneri.tpgwear.api.TpgApi;
import com.mauriciotogneri.tpgwear.api.TpgApi.OnRequestResult;

import java.util.List;

public class WearableService extends Service implements WearableEvents
{
    private WearableConnectivity connectivity;
    private Preferences preferences;

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.preferences = Preferences.getInstance(this);

        this.connectivity = new WearableConnectivity(this, this);
        this.connectivity.connect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onConnectedSuccess()
    {
    }

    @Override
    public void onConnectedFail()
    {
    }

    @Override
    public void onMessageReceived(Message message)
    {
        String nodeId = message.getNodeId();
        String path = message.getPath();
        String payload = message.getPayloadAsString();

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
        List<Stop> stops = preferences.getFavoriteStops();

        connectivity.sendMessage(Messages.resultFavoriteStops(nodeId, stops));
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

                        connectivity.sendMessage(Messages.resultDepartures(nodeId, nextDepartures.departures));
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
                connectivity.sendMessage(Messages.resultTrip(nodeId, trip.steps));
            }

            @Override
            public void onFailure()
            {
            }
        });
    }

    private void increaseStopHitCount(String stopCode)
    {
        preferences.increaseHitCount(stopCode);
    }
}