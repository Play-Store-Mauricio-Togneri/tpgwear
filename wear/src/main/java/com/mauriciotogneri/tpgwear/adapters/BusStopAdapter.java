package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mauriciotogneri.common.model.BusStop;
import com.mauriciotogneri.tpgwear.R;

import java.util.List;

public class BusStopAdapter extends WearableListView.Adapter
{
    private final List<BusStop> items;
    private final LayoutInflater inflater;

    public BusStopAdapter(Context context, List<BusStop> items)
    {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
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
        busStopViewHolder.name.setText(busStop.getName());
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public static class BusStopViewHolder extends WearableListView.ViewHolder
    {
        private TextView name;

        public BusStopViewHolder(View itemView)
        {
            super(itemView);

            this.name = (TextView) itemView.findViewById(R.id.name);
        }

        public BusStop getBusStop()
        {
            return (BusStop) itemView.getTag();
        }
    }
}