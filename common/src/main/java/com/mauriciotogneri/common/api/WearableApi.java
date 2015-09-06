package com.mauriciotogneri.common.api;

import com.mauriciotogneri.common.Message;
import com.mauriciotogneri.common.model.BusStopList;

public class WearableApi
{
    public static final class Paths
    {
        public static final String GET_FAVORITE_STOPS = "/get_favorite_stops";
        public static final String RESULT_FAVORITE_STOPS = "/result_favorite_stops";
    }

    public static final class Calls
    {
        public static Message getFavoriteStops(String nodeId)
        {
            return new Message(nodeId, Paths.GET_FAVORITE_STOPS);
        }

        public static Message resultFavoriteStops(String nodeId, BusStopList busStopList)
        {
            return new Message(nodeId, Paths.RESULT_FAVORITE_STOPS, busStopList.toString());
        }
    }
}