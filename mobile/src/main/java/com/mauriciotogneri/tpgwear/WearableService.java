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

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi.SendMessageResult;
import com.mauriciotogneri.common.Message;
import com.mauriciotogneri.common.WearableConnectivity;
import com.mauriciotogneri.common.WearableConnectivity.OnConnectionEvent;
import com.mauriciotogneri.common.WearableConnectivity.OnMessageReceived;
import com.mauriciotogneri.common.api.TpgApi;
import com.mauriciotogneri.common.api.TpgApi.OnHttpResult;
import com.mauriciotogneri.common.api.WearableApi.Calls;
import com.mauriciotogneri.common.api.WearableApi.Paths;
import com.mauriciotogneri.common.model.BusStopList;
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

        //        BusStopList busStopList = new BusStopList();
        //        busStopList.add(new BusStop("A", "1"));
        //        busStopList.add(new BusStop("B", "2"));
        //        busStopList.add(new BusStop("C", "3"));
        //        preferences.saveFavoriteStops(busStopList);
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
        toast("CLIENT CONNECTED");
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
        byte[] payload = message.getPayloadAsBytes();

        if (TextUtils.equals(path, Paths.GET_FAVORITE_STOPS))
        {
            getFavoriteStops(nodeId);
        }
    }

    private void getFavoriteStops(final String nodeId)
    {
        BusStopList busStopList = preferences.getFavoriteStops();
        wearableConnectivity.sendMessage(Calls.resultFavoriteStops(nodeId, busStopList), new ResultCallback<SendMessageResult>()
        {
            @Override
            public void onResult(SendMessageResult sendMessageResult)
            {
                toast("MESSAGE SENT: " + sendMessageResult.getStatus().isSuccess() + " -> " + nodeId);
            }
        });
    }

    private void getStops(final String nodeId)
    {
        tpgApi.getStops(new OnHttpResult<BusStopList>()
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