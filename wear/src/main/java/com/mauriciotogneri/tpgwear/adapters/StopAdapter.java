package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.common.model.BusStop;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StopAdapter.BusStopViewHolder;

public class StopAdapter extends BaseAdapter<BusStop, BusStopViewHolder>
{
    public StopAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected void fill(BusStop stop, BusStopViewHolder viewHolder)
    {
        viewHolder.busStopName.setText(stop.getName());
    }

    @Override
    protected BusStopViewHolder getViewHolder(View view)
    {
        return new BusStopViewHolder(view);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.row_stop;
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