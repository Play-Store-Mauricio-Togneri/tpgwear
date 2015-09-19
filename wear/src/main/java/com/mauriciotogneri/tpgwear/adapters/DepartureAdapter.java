package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.wearable.view.WearableListView;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.json.Departure;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.DepartureAdapter.DepartureViewHolder;

public class DepartureAdapter extends BaseAdapter<Departure, DepartureViewHolder>
{
    public DepartureAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected void fill(Departure departure, DepartureViewHolder viewHolder)
    {
        viewHolder.line.setText(departure.line.lineCode);
        GradientDrawable background = (GradientDrawable) viewHolder.line.getBackground();
        background.setStroke(5, Color.RED);
        background.setStroke(5, Color.parseColor("#" + departure.color));

        viewHolder.destination.setText(departure.line.destinationName);
        viewHolder.waitingTime.setText(departure.getFormattedWaitingTime());
    }

    @Override
    protected DepartureViewHolder getViewHolder(View view)
    {
        return new DepartureViewHolder(view);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.row_departure;
    }

    public static class DepartureViewHolder extends WearableListView.ViewHolder
    {
        private final TextView line;
        private final TextView destination;
        private final TextView waitingTime;

        public DepartureViewHolder(View itemView)
        {
            super(itemView);

            this.line = (TextView) itemView.findViewById(R.id.line_code);
            this.destination = (TextView) itemView.findViewById(R.id.destination);
            this.waitingTime = (TextView) itemView.findViewById(R.id.waiting_time);
        }

        public Departure getDeparture()
        {
            return (Departure) itemView.getTag();
        }
    }
}