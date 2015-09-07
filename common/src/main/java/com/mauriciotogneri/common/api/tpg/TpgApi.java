package com.mauriciotogneri.common.api.tpg;

import android.util.Log;

import com.mauriciotogneri.common.api.tpg.json.AllBusStopResponse;
import com.mauriciotogneri.common.api.tpg.json.DeparturesResponse;
import com.mauriciotogneri.common.api.tpg.json.DeparturesResponse.ProchainDepart;
import com.mauriciotogneri.common.model.BusLine;
import com.mauriciotogneri.common.model.BusLineList;
import com.mauriciotogneri.common.model.BusStopDeparture;
import com.mauriciotogneri.common.model.BusStopDepartureList;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.commons.lang3.text.WordUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

public class TpgApi
{
    private final OkHttpClient client = new OkHttpClient();

    public void getBusLines(final OnRequestResult<BusLineList> onRequestResult)
    {
        String url = "http://pitre-iphone.tpg.ch/GetCouleursLignes.json";

        getHttpResponse(url, new OnHttpResult()
        {
            @Override
            public void onSuccess(String result)
            {
                BusLineList busLineList = new BusLineList();

                AllBusStopResponse allBusStopResponse = JsonUtils.fromJson(result, AllBusStopResponse.class);

                for (AllBusStopResponse.Couleur couleur : allBusStopResponse.couleurs.couleur)
                {
                    busLineList.add(new BusLine(couleur.ligne, "#" + couleur.hexa));
                }

                onRequestResult.onSuccess(busLineList);
            }

            @Override
            public void onFailure()
            {
                onRequestResult.onFailure();
            }
        });
    }

    public void getBusStopDepartures(String busStopCode, final BusLineList busLineList, final OnRequestResult<BusStopDepartureList> onRequestResult)
    {
        String url = "http://pitre-iphone.tpg.ch/GetProchainsDepartsTriHeure.json?codeArret=" + busStopCode;

        getHttpResponse(url, new OnHttpResult()
        {
            @Override
            public void onSuccess(String result)
            {
                BusStopDepartureList busStopDepartureList = new BusStopDepartureList();

                DeparturesResponse departuresResponse = JsonUtils.fromJson(result, DeparturesResponse.class);

                List<ProchainDepart> prochainDeparts = departuresResponse.prochainsDeparts.prochainDepart;

                for (ProchainDepart prochainDepart : prochainDeparts)
                {
                    BusLine busLine = busLineList.getByName(prochainDepart.ligne);

                    if (busLine != null)
                    {
                        String destination = WordUtils.capitalize(prochainDepart.destinationMajuscule.toLowerCase());

                        BusStopDeparture busStopDeparture = new BusStopDeparture(busLine, destination, prochainDepart.attenteMilli);
                        busStopDepartureList.add(busStopDeparture);
                    }
                }

                onRequestResult.onSuccess(busStopDepartureList);
            }

            @Override
            public void onFailure()
            {
                onRequestResult.onFailure();
            }
        });
    }

    private void getHttpResponse(String url, final OnHttpResult onHttpResult)
    {
        Request.Builder builder = new Request.Builder();
        builder.url(url);

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
                        onHttpResult.onSuccess(response.body().string());
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

    // TODO
    public void test()
    {
        getHttpResponse("http://pitre-iphone.tpg.ch/GetProchainsDepartsTriHeure.json?codeArret=HOPI", new OnHttpResult()
        {
            @Override
            public void onSuccess(String result)
            {
                DeparturesResponse departuresResponse = JsonUtils.fromJson(result, DeparturesResponse.class);
                List<ProchainDepart> list = departuresResponse.prochainsDeparts.prochainDepart;

                for (ProchainDepart prochainDepart : list)
                {
                    if (prochainDepart.destinationMajuscule.equals("AEROPORT"))
                    {
                        String text = prochainDepart.destination;

                        SortedMap charsets = Charset.availableCharsets();
                        Set names = charsets.keySet();

                        for (Iterator e = names.iterator(); e.hasNext(); )
                        {
                            String name = (String) e.next();
                            Charset charset = (Charset) charsets.get(name);

                            try
                            {
                                String a = new String(text.getBytes(), charset.name());
                                Log.e("ERROR2", charset.name() + ": " + a);
                            }
                            catch (UnsupportedEncodingException e1)
                            {
                                e1.printStackTrace();
                            }
                        }

                        break;
                    }
                }
            }

            @Override
            public void onFailure()
            {

            }
        });
    }

    public interface OnHttpResult
    {
        void onSuccess(String result);

        void onFailure();
    }

    public interface OnRequestResult<T>
    {
        void onSuccess(T result);

        void onFailure();
    }
}