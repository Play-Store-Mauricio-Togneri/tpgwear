package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StopAdapter.StopViewHolder;

public class StopAdapter extends BaseAdapter<Stop, StopViewHolder>
{
    public StopAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected void fill(Stop stop, StopViewHolder viewHolder)
    {
        viewHolder.stopName.setText(stop.stopName);
    }

    @Override
    protected StopViewHolder getViewHolder(View view)
    {
        return new StopViewHolder(view);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.row_stop;
    }

    public static class StopViewHolder extends WearableListView.ViewHolder
    {
        private final TextView stopName;

        public StopViewHolder(View itemView)
        {
            super(itemView);

            this.stopName = (TextView) itemView.findViewById(R.id.stop_name);
        }

        public Stop getStop()
        {
            return (Stop) itemView.getTag();
        }
    }
}