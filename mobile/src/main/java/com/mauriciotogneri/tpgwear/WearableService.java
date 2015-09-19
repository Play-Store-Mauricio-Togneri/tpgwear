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
import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.api.wearable.Message;
import com.mauriciotogneri.common.api.wearable.WearableApi.Messages;
import com.mauriciotogneri.common.api.wearable.WearableApi.Paths;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity;
import com.mauriciotogneri.common.api.wearable.WearableConnectivity.WearableEvents;
import com.mauriciotogneri.common.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

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

        if (TextUtils.equals(path, Paths.GET_FAVORITE_STOPS))
        {
            getFavoriteStops(nodeId);
        }
        else if (TextUtils.equals(path, Paths.GET_DEPARTURES))
        {
            getDepartures(nodeId, payload);
        }
    }

    private void getFavoriteStops(String nodeId)
    {
        //List<Stop> stops = preferences.getFavoriteStops();
        List<Stop> stops = getDefaultStopList();

        connectivity.sendMessage(Messages.resultFavoriteStops(nodeId, stops));
    }

    // TODO: remove
    private List<Stop> getDefaultStopList()
    {
        Stop stopCamilleMartin = new Stop();
        stopCamilleMartin.stopCode = "CMAR";
        stopCamilleMartin.stopName = "Camille-Martin";

        Stop stopEpinettes = new Stop();
        stopEpinettes.stopCode = "EPIN";
        stopEpinettes.stopName = "Epinettes";

        Stop stopMileant = new Stop();
        stopMileant.stopCode = "MILE";
        stopMileant.stopName = "Miléant";

        Stop stopBelAir = new Stop();
        stopBelAir.stopCode = "BAIR";
        stopBelAir.stopName = "Bel-Air";

        Stop stopBains = new Stop();
        stopBains.stopCode = "BANS";
        stopBains.stopName = "Bains";

        Stop stopPalettes = new Stop();
        stopPalettes.stopCode = "PALE";
        stopPalettes.stopName = "Palettes";

        List<Stop> stops = new ArrayList<>();
        stops.add(stopCamilleMartin);
        stops.add(stopEpinettes);
        stops.add(stopMileant);
        stops.add(stopBelAir);
        stops.add(stopBains);
        stops.add(stopPalettes);

        return stops;
    }

    private void getDepartures(final String nodeId, final String stopCode)
    {
        TpgApi tpgApi = TpgApi.getInstance(this);
        tpgApi.getNextDepartures(stopCode, new OnRequestResult<GetNextDepartures>()
        {
            @Override
            public void onSuccess(GetNextDepartures result)
            {
                connectivity.sendMessage(Messages.resultDepartures(nodeId, result.departures));
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