package com.mauriciotogneri.tpgwear;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mauriciotogneri.common.api.tpg.TpgApi;
import com.mauriciotogneri.common.api.tpg.TpgApi.OnRequestResult;
import com.mauriciotogneri.common.api.tpg.json.GetNextDepartures;
import com.mauriciotogneri.common.api.wearable.Message;
import com.mauriciotogneri.common.api.wearable.WearableApi.Messages;
import com.mauriciotogneri.common.api.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.common.model.BusStop;
import com.mauriciotogneri.common.model.BusStopList;
import com.mauriciotogneri.common.utils.Preferences;

public class WearableService extends Service implements WearableEvents
{
    private WearableConnectivity connectivity;
    private Preferences preferences;

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.preferences = new Preferences(this);

        this.connectivity = new WearableConnectivity(this, this);
        this.connectivity.connect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null; // TODO
    }

    @Override
    public void onConnectedSuccess()
    {
    }

    @Override
    public void onConnectedFail()
    {
        toast("CLIENT DISCONNECTED");
    }

    @Override
    public void onMessageReceived(Message message)
    {
        String nodeId = message.getNodeId();
        String path = message.getPath();
        String payload = message.getPayloadAsString();

        if (TextUtils.equals(path, Paths.GET_FAVORITE_BUS_STOPS))
        {
            getFavoriteStops(nodeId);
        }
        else if (TextUtils.equals(path, Paths.GET_BUS_STOP_DEPARTURES))
        {
            getBusStopDepartures(nodeId, payload);
        }
    }

    private void getFavoriteStops(String nodeId)
    {
        //BusStopList busStopList = preferences.getFavoriteBusStops();
        BusStopList busStopList = getDefaultBusStopList();

        connectivity.sendMessage(Messages.resultFavoriteBusStops(nodeId, busStopList));
    }

    // TODO: remove
    private BusStopList getDefaultBusStopList()
    {
        BusStop busStopCamilleMartin = new BusStop("Camille-Martin", "CMAR");
        BusStop busStopEpinettes = new BusStop("Epinettes", "EPIN");
        BusStop busStopMileant = new BusStop("Miléant", "MILE");
        BusStop busStopBelAir = new BusStop("Bel-Air", "BAIR");
        BusStop busStopBains = new BusStop("Bains", "BANS");
        BusStop busStopPalettes = new BusStop("Palettes", "PALE");

        BusStopList busStopList = new BusStopList();
        busStopList.add(busStopCamilleMartin);
        busStopList.add(busStopEpinettes);
        busStopList.add(busStopMileant);
        busStopList.add(busStopBelAir);
        busStopList.add(busStopBains);
        busStopList.add(busStopPalettes);

        return busStopList;
    }

    private void getBusStopDepartures(final String nodeId, final String busStopCode)
    {
        TpgApi tpgApi = TpgApi.getInstance(this);
        tpgApi.getNextDepartures(busStopCode, new OnRequestResult<GetNextDepartures>()
        {
            @Override
            public void onSuccess(GetNextDepartures result)
            {
                connectivity.sendMessage(Messages.resultBusStopDepartures(nodeId, result.departures));
            }

            @Override
            public void onFailure()
            {
                toast("HTTP CALL FAIL");
            }
        });
    }

    private void toast(final String message)
    {
        Log.e("ERROR", message);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(WearableService.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}