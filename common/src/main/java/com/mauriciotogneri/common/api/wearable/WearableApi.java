package com.mauriciotogneri.common.api.wearable;

import com.mauriciotogneri.common.api.tpg.json.Departure;
import com.mauriciotogneri.common.api.tpg.json.Step;
import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.utils.JsonUtils;

import java.util.List;

public class WearableApi
{
    private WearableApi()
    {
    }

    public static final class Paths
    {
        public static final String GET_FAVORITE_STOPS = "/get_favorite_stops";
        public static final String RESULT_FAVORITE_STOPS = "/result_favorite_stops";

        public static final String GET_DEPARTURES = "/get_departures";
        public static final String RESULT_DEPARTURES = "/result_departures";

        public static final String GET_TRIP = "/get_trip";
        public static final String RESULT_TRIP = "/result_trip";

        public static final String INCREASE_STOP_HIT_COUNT = "/increase_stop_hint_count";
    }

    public static final class Messages
    {
        public static Message getFavoriteStops(String nodeId)
        {
            return new Message(nodeId, Paths.GET_FAVORITE_STOPS);
        }

        public static Message resultFavoriteStops(String nodeId, List<Stop> stops)
        {
            return new Message(nodeId, Paths.RESULT_FAVORITE_STOPS, JsonUtils.toJson(stops));
        }

        public static Message getDepartures(String nodeId, String stopCode)
        {
            return new Message(nodeId, Paths.GET_DEPARTURES, stopCode);
        }

        public static Message resultDepartures(String nodeId, List<Departure> departures)
        {
            return new Message(nodeId, Paths.RESULT_DEPARTURES, JsonUtils.toJson(departures));
        }

        public static Message getTrip(String nodeId, String departureCode)
        {
            return new Message(nodeId, Paths.GET_TRIP, departureCode);
        }

        public static Message resultTrip(String nodeId, List<Step> steps)
        {
            return new Message(nodeId, Paths.RESULT_TRIP, JsonUtils.toJson(steps));
        }

        public static Message increaseStopHitCount(String nodeId, String stopCode)
        {
            return new Message(nodeId, Paths.INCREASE_STOP_HIT_COUNT, stopCode);
        }
    }
}