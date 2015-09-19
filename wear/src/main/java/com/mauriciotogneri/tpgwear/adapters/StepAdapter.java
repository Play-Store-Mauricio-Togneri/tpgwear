package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.json.Step;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StepAdapter.StepViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StepAdapter extends BaseAdapter<Step, StepViewHolder>
{
    private long currentTimestamp = System.currentTimeMillis();

    private static final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
    private static final SimpleDateFormat stepFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public StepAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected void fill(Step step, StepViewHolder viewHolder)
    {
        viewHolder.destination.setText(step.stop.stopName);

        Date date = getDate(step.timestamp);

        if (date != null)
        {
            long timeDiff = (date.getTime() - currentTimestamp) / (1000 * 60);

            if (timeDiff > 0)
            {
                viewHolder.waitingTimeRelative.setText(timeDiff + "'");
            }
            else
            {
                viewHolder.waitingTimeRelative.setText("");
            }

            viewHolder.waitingTimeAbsolute.setText(stepFormatter.format(date));
        }
        else
        {
            viewHolder.waitingTimeRelative.setText("?");
            viewHolder.waitingTimeAbsolute.setText("?");
        }
    }

    private Date getDate(String timestamp)
    {
        try
        {
            return timestampFormatter.parse(timestamp);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected StepViewHolder getViewHolder(View view)
    {
        return new StepViewHolder(view);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.row_step;
    }

    public static class StepViewHolder extends BaseViewHolder<Step>
    {
        private final TextView destination;
        private final TextView waitingTimeRelative;
        private final TextView waitingTimeAbsolute;

        public StepViewHolder(View itemView)
        {
            super(itemView);

            this.destination = (TextView) itemView.findViewById(R.id.destination);
            this.waitingTimeRelative = (TextView) itemView.findViewById(R.id.waiting_time_relative);
            this.waitingTimeAbsolute = (TextView) itemView.findViewById(R.id.waiting_time_absolute);
        }
    }
}