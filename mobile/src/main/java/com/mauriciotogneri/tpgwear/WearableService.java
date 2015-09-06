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
import com.mauriciotogneri.common.api.tpg.TpgApi.OnHttpResult;
import com.mauriciotogneri.common.api.wearable.WearableApi.Calls;
import com.mauriciotogneri.common.api.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.OnConnectionEvent;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.OnMessageReceived;
import com.mauriciotogneri.common.model.BusLine;
import com.mauriciotogneri.common.model.BusStop;
import com.mauriciotogneri.common.model.BusStopDepartureList;
import com.mauriciotogneri.common.model.BusStopList;
import com.mauriciotogneri.common.model.Message;
import com.mauriciotogneri.common.utils.Preferences;

public class WearableService extends Service implements OnConnectionEvent, OnMessageReceived
{
    private WearableConnectivity wearableConnectivity;
    private final TpgApi tpgApi = new TpgApi();
    private Preferences preferences;

    @Override
    public void onCreate()
    {
        super.onCreate();

        this.preferences = new Preferences(this);

        this.wearableConnectivity = new WearableConnectivity(this, this, this);
        this.wearableConnectivity.connect();
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

        wearableConnectivity.sendMessage(Calls.resultFavoriteBusStops(nodeId, busStopList));
    }

    private void getBusStopDepartures(final String nodeId, String busStopCode)
    {
        tpgApi.getBusStopDepartures(busStopCode, new OnHttpResult<BusStopDepartureList>()
        {
            @Override
            public void onSuccess(BusStopDepartureList busStopDepartureList)
            {
                //BusStopDepartureList busStopDepartureList = getDefaultBusStopDepartureList();

                wearableConnectivity.sendMessage(Calls.resultBusStopDepartures(nodeId, busStopDepartureList));
            }

            @Override
            public void onFailure()
            {

            }
        });
    }

    private BusStopList getDefaultBusStopList()
    {
        BusLine busLine2 = new BusLine("2", "#CCCC33");
        BusLine busLine7 = new BusLine("7", "#009933");
        BusLine busLine9 = new BusLine("9", "#CC0033");
        BusLine busLine11 = new BusLine("11", "#993399");
        BusLine busLine19 = new BusLine("19", "#FFCC00");
        BusLine busLine22 = new BusLine("22", "#5A1E82");
        BusLine busLine23 = new BusLine("23", "#CC3399");

        BusStop busStopCamilleMartin = new BusStop("Camille-Martin", "CMAR");
        busStopCamilleMartin.addLine(busLine7);
        busStopCamilleMartin.addLine(busLine9);

        BusStop busStopMileant = new BusStop("Miléant", "MILE");
        busStopMileant.addLine(busLine7);
        busStopMileant.addLine(busLine9);
        busStopMileant.addLine(busLine11);

        BusStop busStopBains = new BusStop("Bains", "BANS");
        busStopBains.addLine(busLine2);
        busStopBains.addLine(busLine19);

        BusStop busStopPalettes = new BusStop("Palettes", "PALE");
        busStopPalettes.addLine(busLine22);
        busStopPalettes.addLine(busLine23);

        BusStopList busStopList = new BusStopList();
        busStopList.add(busStopCamilleMartin);
        busStopList.add(busStopMileant);
        busStopList.add(busStopBains);
        busStopList.add(busStopPalettes);

        return busStopList;
    }

    private void getStops(final String nodeId)
    {
        tpgApi.getBusStops(new OnHttpResult<BusStopList>()
        {
            @Override
            public void onSuccess(BusStopList busStopList)
            {
                // TODO
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