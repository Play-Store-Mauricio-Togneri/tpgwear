package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.json.Departure;
import com.mauriciotogneri.common.model.BusStopDeparture;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StopDepartureAdapter.BusStopDepartureViewHolder;

public class StopDepartureAdapter extends BaseAdapter<Departure, BusStopDepartureViewHolder>
{
    public StopDepartureAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected void fill(Departure departure, BusStopDepartureViewHolder viewHolder)
    {
        viewHolder.busLine.setText(departure.line.lineCode);
        GradientDrawable background = (GradientDrawable) viewHolder.busLine.getBackground();
        background.setStroke(5, Color.RED);
        //background.setStroke(5, Color.parseColor(busStopDeparture.getBusLine().getColor()));

        viewHolder.destination.setText(departure.line.destinationName);
        viewHolder.waitingTime.setText(departure.waitingTime);
    }

    @Override
    protected BusStopDepartureViewHolder getViewHolder(View view)
    {
        return new BusStopDepartureViewHolder(view);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.row_departure;
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