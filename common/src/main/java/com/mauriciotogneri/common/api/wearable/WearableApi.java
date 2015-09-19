package com.mauriciotogneri.common.api.wearable;

import com.mauriciotogneri.common.model.BusStopDepartureList;
import com.mauriciotogneri.common.model.BusStopList;

public class WearableApi
{
    public static final class Paths
    {
        public static final String GET_FAVORITE_BUS_STOPS = "/get_favorite_bus_stops";
        public static final String RESULT_FAVORITE_BUS_STOPS = "/result_favorite_bus_stops";

        public static final String GET_BUS_STOP_DEPARTURES = "/get_bus_stop_departures";
        public static final String RESULT_BUS_STOP_DEPARTURES = "/result_bus_stop_departures";
    }

    public static final class Messages
    {
        public static Message getFavoriteBusStops(String nodeId)
        {
            return new Message(nodeId, Paths.GET_FAVORITE_BUS_STOPS);
        }

        public static Message resultFavoriteBusStops(String nodeId, BusStopList busStopList)
        {
            return new Message(nodeId, Paths.RESULT_FAVORITE_BUS_STOPS, busStopList.toString());
        }

        public static Message getBusStopDepartures(String nodeId, String busStopCode)
        {
            return new Message(nodeId, Paths.GET_BUS_STOP_DEPARTURES, busStopCode);
        }

        public static Message resultBusStopDepartures(String nodeId, BusStopDepartureList busStopDepartureList)
        {
            return new Message(nodeId, Paths.RESULT_BUS_STOP_DEPARTURES, busStopDepartureList.toString());
        }
    }
}