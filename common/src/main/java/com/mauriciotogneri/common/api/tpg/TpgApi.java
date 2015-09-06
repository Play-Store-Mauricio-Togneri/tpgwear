package com.mauriciotogneri.common.api.tpg;

import com.mauriciotogneri.common.api.tpg.json.DeparturesResponse;
import com.mauriciotogneri.common.api.tpg.json.DeparturesResponse.ProchainDepart;
import com.mauriciotogneri.common.model.BusLine;
import com.mauriciotogneri.common.model.BusStopDeparture;
import com.mauriciotogneri.common.model.BusStopDepartureList;
import com.mauriciotogneri.common.model.BusStopList;
import com.mauriciotogneri.common.utils.EncodingHelper;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

public class TpgApi
{
    private final OkHttpClient client = new OkHttpClient();

    public void getBusStops(final OnHttpResult<BusStopList> onHttpResult)
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

    public void getBusStopDepartures(String busStopCode, final OnHttpResult<BusStopDepartureList> onHttpResult)
    {
        Request.Builder builder = new Request.Builder();
        builder.url("http://pitre-iphone.tpg.ch/GetProchainsDepartsTriHeure.json?codeArret=" + busStopCode);

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
                        BusStopDepartureList busStopDepartureList = new BusStopDepartureList();

                        DeparturesResponse departuresResponse = JsonUtils.fromJson(response.body().charStream(), DeparturesResponse.class);

                        List<ProchainDepart> prochainDeparts = departuresResponse.prochainsDeparts.prochainDepart;

                        for (ProchainDepart prochainDepart : prochainDeparts)
                        {
                            BusLine busLine = new BusLine(String.valueOf(prochainDepart.ligne), ""); // TODO
                            String destination = EncodingHelper.decodeFromIso(prochainDepart.destination);

                            BusStopDeparture busStopDeparture = new BusStopDeparture(busLine, destination, prochainDepart.attenteMilli);
                            busStopDepartureList.add(busStopDeparture);
                        }

                        onHttpResult.onSuccess(busStopDepartureList);
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