package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mauriciotogneri.common.model.BusStopDeparture;
import com.mauriciotogneri.tpgwear.R;

import java.util.List;

public class BusStopDepartureAdapter extends WearableListView.Adapter
{
    private final List<BusStopDeparture> items;
    private final LayoutInflater inflater;

    public BusStopDepartureAdapter(Context context, List<BusStopDeparture> items)
    {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new BusStopDepartureViewHolder(inflater.inflate(R.layout.row_bus_stop_departure, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, final int position)
    {
        BusStopDeparture busStopDeparture = items.get(position);
        viewHolder.itemView.setTag(busStopDeparture);

        BusStopDepartureViewHolder busStopDepartureViewHolder = (BusStopDepartureViewHolder) viewHolder;

        busStopDepartureViewHolder.busLine.setText(busStopDeparture.getBusLine().getName());
        GradientDrawable background = (GradientDrawable) busStopDepartureViewHolder.busLine.getBackground();
        background.setStroke(5, Color.parseColor(busStopDeparture.getBusLine().getColor()));

        busStopDepartureViewHolder.destination.setText(busStopDeparture.getDestination());
        busStopDepartureViewHolder.waitingTime.setText(busStopDeparture.getWaitingTime());
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public static class BusStopDepartureViewHolder extends WearableListView.ViewHolder
    {
        private TextView busLine;
        private TextView destination;
        private TextView waitingTime;

        public BusStopDepartureViewHolder(View itemView)
        {
            super(itemView);

            this.busLine = (TextView) itemView.findViewById(R.id.bus_line);
            this.destination = (TextView) itemView.findViewById(R.id.destination);
            this.waitingTime = (TextView) itemView.findViewById(R.id.waiting_time);
        }

        public BusStopDeparture getBusStopDeparture()
        {
            return (BusStopDeparture) itemView.getTag();
        }
    }
}