package com.mauriciotogneri.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.reflect.TypeToken;
import com.mauriciotogneri.common.api.tpg.json.Stop;

import java.lang.reflect.Type;
import java.util.List;

public class Preferences
{
    private static final String KEY_FAVORITE_STOPS = "KEY_FAVORITE_STOPS";

    private final SharedPreferences sharedPreferences;

    public Preferences(Context context)
    {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // =============================================================================================

    @SuppressLint("CommitPrefEdits")
    public void clear()
    {
        sharedPreferences.edit().clear().commit();
    }

    private boolean contains(String key)
    {
        return sharedPreferences.contains(key);
    }

    @SuppressLint("CommitPrefEdits")
    private void remove(String key)
    {
        sharedPreferences.edit().remove(key).commit();
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

    // =============================================================================================

    public void saveFavoriteStops(List<Stop> stops)
    {
        save(KEY_FAVORITE_STOPS, JsonUtils.toJson(stops));
    }

    public List<Stop> getFavoriteStops()
    {
        String json = get(KEY_FAVORITE_STOPS, "[]");

        Type type = new TypeToken<List<Stop>>()
        {
        }.getType();

        return JsonUtils.fromJson(json, type);
    }
}