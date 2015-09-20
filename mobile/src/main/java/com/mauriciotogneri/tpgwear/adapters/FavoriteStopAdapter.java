package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.FavoriteStopAdapter.ViewHolder;

import java.util.ArrayList;

public class FavoriteStopAdapter extends BaseAdapter<Stop, ViewHolder>
{
    public FavoriteStopAdapter(Context context)
    {
        super(context, R.layout.row_favorite_stop, new ArrayList<Stop>());
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Stop stop, int position, View rowView)
    {
        viewHolder.stopName.setText(stop.stopName);
    }

    @Override
    protected ViewHolder getViewHolder(View view)
    {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.stopName = (TextView) view.findViewById(R.id.stop_name);

        return viewHolder;
    }

    public static class ViewHolder
    {
        TextView stopName;
    }
}