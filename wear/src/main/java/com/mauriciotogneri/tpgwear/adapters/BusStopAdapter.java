package com.mauriciotogneri.tpgwear.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mauriciotogneri.common.model.BusStop;
import com.mauriciotogneri.tpgwear.R;

import java.util.ArrayList;
import java.util.List;

public class BusStopAdapter extends WearableListView.Adapter
{
    private final List<BusStop> items;
    private final LayoutInflater inflater;

    public BusStopAdapter(Context context)
    {
        this.items = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<BusStop> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    @SuppressLint("InflateParams")
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new BusStopViewHolder(inflater.inflate(R.layout.row_bus_stop, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder viewHolder, final int position)
    {
        BusStop busStop = items.get(position);
        viewHolder.itemView.setTag(busStop);

        BusStopViewHolder busStopViewHolder = (BusStopViewHolder) viewHolder;
        busStopViewHolder.busStopName.setText(busStop.getName());
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public static class BusStopViewHolder extends WearableListView.ViewHolder
    {
        private TextView busStopName;

        public BusStopViewHolder(View itemView)
        {
            super(itemView);

            this.busStopName = (TextView) itemView.findViewById(R.id.bus_stop_name);
        }

        public BusStop getBusStop()
        {
            return (BusStop) itemView.getTag();
        }
    }
}