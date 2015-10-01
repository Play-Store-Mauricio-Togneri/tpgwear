package com.mauriciotogneri.tpgwear.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mauriciotogneri.common.api.tpg.Stop;
import com.mauriciotogneri.common.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Preferences
{
    private final SharedPreferences sharedPreferences;

    private static Preferences instance;

    private static final String KEY_FAVORITE_STOPS = "KEY_FAVORITE_STOPS";

    private Preferences(Context context)
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static synchronized Preferences getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new Preferences(context);
        }

        return instance;
    }

    @SuppressLint("CommitPrefEdits")
    private void save(String key, String value)
    {
        sharedPreferences.edit().putString(key, value).commit();
    }

    private String get(String key, String defaultValue)
    {
        return sharedPreferences.getString(key, defaultValue);
    }

    public synchronized List<Stop> addFavoriteStop(Stop stop)
    {
        List<Stop> stops = getFavoriteStops();

        if (!stops.contains(stop))
        {
            stops.add(stop);
            saveFavoriteStops(stops);
        }

        return stops;
    }

    public synchronized List<Stop> removeFavoriteStop(Stop stop)
    {
        List<Stop> stops = getFavoriteStops();

        if (stops.contains(stop))
        {
            stops.remove(stop);
            saveFavoriteStops(stops);
        }

        return stops;
    }

    private synchronized void saveFavoriteStops(List<Stop> stops)
    {
        save(KEY_FAVORITE_STOPS, JsonUtils.toJson(stops));
    }

    public synchronized List<Stop> getFavoriteStops()
    {
        String json = get(KEY_FAVORITE_STOPS, "[]");

        Type type = new TypeToken<List<Stop>>()
        {
        }.getType();

        List<Stop> stops = JsonUtils.fromJson(json, type);

        Collections.sort(stops, new Comparator<Stop>()
        {
            @Override
            public int compare(Stop lhs, Stop rhs)
            {
                int hitComparison = rhs.hitCount - lhs.hitCount;

                return (hitComparison != 0) ? hitComparison : lhs.stopName.compareToIgnoreCase(rhs.stopName);
            }
        });

        return stops;
    }

    public synchronized void increaseHitCount(String stopCode)
    {
        List<Stop> stops = getFavoriteStops();

        for (Stop stop : stops)
        {
            if (TextUtils.equals(stop.stopCode, stopCode))
            {
                stop.hitCount++;
                saveFavoriteStops(stops);
                break;
            }
        }
    }
}