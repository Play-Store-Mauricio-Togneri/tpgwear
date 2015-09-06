package com.mauriciotogneri.common.api;

import com.mauriciotogneri.common.model.BusStopList;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class TpgApi
{
    private final OkHttpClient client = new OkHttpClient();

    public void getStops(final OnHttpResult<BusStopList> onHttpResult)
    {
        Request.Builder builder = new Request.Builder();
        builder.url("http://pitre-iphone.tpg.ch/GetTousArrets.json");

        Request request = builder.build();

        try
        {
            client.newCall(request).enqueue(new Callback()
            {
                @Override
                public void onResponse(Response response) throws IOException
                {
                    if (response.isSuccessful())
                    {
                        BusStopList busStopList = JsonUtils.fromJson(response.body().charStream(), BusStopList.class);
                        onHttpResult.onSuccess(busStopList);
                    }
                    else
                    {
                        onHttpResult.onFailure();
                    }
                }

                @Override
                public void onFailure(Request request, IOException e)
                {
                    onHttpResult.onFailure();
                }
            });
        }
        catch (Exception e)
        {
            onHttpResult.onFailure();
        }
    }

    public interface OnHttpResult<T>
    {
        void onSuccess(T result);

        void onFailure();
    }
}