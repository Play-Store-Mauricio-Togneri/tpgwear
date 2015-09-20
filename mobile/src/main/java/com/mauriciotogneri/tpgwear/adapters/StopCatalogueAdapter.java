package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StopCatalogueAdapter.ViewHolder;

import java.util.ArrayList;

public class StopCatalogueAdapter extends BaseAdapter<Stop, ViewHolder>
{
    public StopCatalogueAdapter(Context context)
    {
        super(context, R.layout.row_stop_catalogue, new ArrayList<Stop>());
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Stop stop, int position, View rowView)
    {
        viewHolder.stopName.setText(stop.stopName);

        if (stop.favorite)
        {
            viewHolder.isFavorite.setImageResource(android.R.drawable.star_on);
        }
        else
        {
            viewHolder.isFavorite.setImageResource(android.R.drawable.star_off);
        }
    }

    @Override
    protected ViewHolder getViewHolder(View view)
    {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.stopName = (TextView) view.findViewById(R.id.stop_name);
        viewHolder.isFavorite = (ImageView) view.findViewById(R.id.is_favorite);

        return viewHolder;
    }

    public static class ViewHolder
    {
        TextView stopName;
        ImageView isFavorite;
    }
}