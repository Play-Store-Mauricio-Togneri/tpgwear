package com.mauriciotogneri.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mauriciotogneri.common.model.BusStopList;

public class Preferences
{
    private static final String KEY_FAVORITE_STOPS = "KEY_FAVORITE_STOPS";

    private final SharedPreferences sharedPreferences;

    public Preferences(Context context)
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // =============================================================================================

    public void clear()
    {
        sharedPreferences.edit().clear().commit();
    }

    private boolean contains(String key)
    {
        return sharedPreferences.contains(key);
    }

    private void remove(String key)
    {
        sharedPreferences.edit().remove(key).commit();
    }

    private void save(String key, String value)
    {
        sharedPreferences.edit().putString(key, value).commit();
    }

    private String get(String key, String defaultValue)
    {
        return sharedPreferences.getString(key, defaultValue);
    }

    // =============================================================================================

    public void saveFavoriteStops(BusStopList busStopList)
    {
        save(KEY_FAVORITE_STOPS, busStopList.toString());
    }

    public BusStopList getFavoriteStops()
    {
        return BusStopList.fromString(get(KEY_FAVORITE_STOPS, "[]"));
    }
}