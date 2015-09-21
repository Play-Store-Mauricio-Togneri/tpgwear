package com.mauriciotogneri.common.api.tpg;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Handler;
import android.os.Looper;

import com.mauriciotogneri.common.R;
import com.mauriciotogneri.common.api.tpg.json.GetDisruptions;
import com.mauriciotogneri.common.api.tpg.json.GetLinesColors;
import com.mauriciotogneri.common.api.tpg.json.GetNextDepartures;
import com.mauriciotogneri.common.api.tpg.json.GetStops;
import com.mauriciotogneri.common.api.tpg.json.GetThermometer;
import com.mauriciotogneri.common.utils.JsonUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;

public class TpgApi
{
    private final String appKey;
    private final OkHttpClient client;

    private static TpgApi instance = null;

    private static final String BASE_URL = "http://prod.ivtr-od.tpg.ch/v1/";

    private TpgApi(String appKey)
    {
        this.appKey = appKey;
        this.client = new OkHttpClient();
    }

    public static synchronized TpgApi getInstance(Context context)
    {
        if (instance == null)
        {
            String appKey = getAppKey(context.getResources());
            instance = new TpgApi(appKey);
        }

        return instance;
    }

    public void getStops(final OnRequestResult<GetStops> onRequestResult)
    {
        String url = getUrl("GetStops");

        getRequest(url, onRequestResult, GetStops.class);
    }

    public void getLinesColors(final OnRequestResult<GetLinesColors> onRequestResult)
    {
        String url = getUrl("GetLinesColors");

        getRequest(url, onRequestResult, GetLinesColors.class);
    }

    public void getNextDepartures(String stopCode, final OnRequestResult<GetNextDepartures> onRequestResult)
    {
        String url = getUrl("GetNextDepartures", "stopCode", stopCode);

        getRequest(url, onRequestResult, GetNextDepartures.class);
    }

    public void getThermometer(int departureCode, final OnRequestResult<GetThermometer> onRequestResult)
    {
        String url = getUrl("GetThermometer", "departureCode", String.valueOf(departureCode));

        getRequest(url, onRequestResult, GetThermometer.class);
    }

    public void getDisruptions(final OnRequestResult<GetDisruptions> onRequestResult)
    {
        String url = getUrl("GetDisruptions");

        getRequest(url, onRequestResult, GetDisruptions.class);
    }

    private <T> void getRequest(String url, final OnRequestResult<T> onRequestResult, final Class<T> clazz)
    {
        getHttpResponse(url, new OnHttpResult()
        {
            @Override
            public void onSuccess(String result)
            {
                try
                {
                    final T getNextDepartures = JsonUtils.fromJson(result, clazz);

                    runOnMainThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            onRequestResult.onSuccess(getNextDepartures);
                        }
                    });
                }
                catch (Exception e)
                {
                    runOnMainThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            onRequestResult.onFailure();
                        }
                    });
                }
            }

            @Override
            public void onFailure()
            {
                runOnMainThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        onRequestResult.onFailure();
                    }
                });
            }
        });
    }

    private void runOnMainThread(Runnable runnable)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
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

    private String getUrl(String method, String... params)
    {
        StringBuilder url = new StringBuilder(BASE_URL + method + ".json?key=" + appKey);

        for (int i = 0; i < params.length; i += 2)
        {
            url.append("&");
            url.append(params[i]);
            url.append("=");
            url.append(params[i + 1]);
        }

        return url.toString();
    }

    private static String getAppKey(Resources resources)
    {
        XmlResourceParser xrp = resources.getXml(R.xml.keys);

        try
        {
            int eventType = xrp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                if (eventType == XmlPullParser.START_TAG)
                {
                    String name = xrp.getName();
                    String attribute = xrp.getAttributeValue(null, "name");

                    if (name.equalsIgnoreCase("string") && attribute.equalsIgnoreCase("app_key"))
                    {
                        return xrp.nextText();
                    }
                }

                eventType = xrp.next();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    private interface OnHttpResult
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