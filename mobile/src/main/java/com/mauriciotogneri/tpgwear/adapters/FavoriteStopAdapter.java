package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.Stop;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.FavoriteStopAdapter.ViewHolder;

import java.util.ArrayList;

public class FavoriteStopAdapter extends BaseAdapter<Stop, ViewHolder>
{
    private boolean editEnabled = false;

    public FavoriteStopAdapter(Context context)
    {
        super(context, R.layout.row_favorite_stop, new ArrayList<Stop>());
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Stop stop)
    {
        viewHolder.stopName.setText(stop.stopName);

        if (editEnabled)
        {
            viewHolder.buttonRemove.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.buttonRemove.setVisibility(View.GONE);
        }

    }

    @Override
    protected ViewHolder getViewHolder(View view)
    {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.stopName = (TextView) view.findViewById(R.id.stop_name);
        viewHolder.buttonRemove = (ImageView) view.findViewById(R.id.button_remove);

        return viewHolder;
    }

    public void toggleEdit()
    {
        editEnabled = !editEnabled;

        notifyDataSetChanged();
    }

    public void disableEdit()
    {
        editEnabled = false;

        notifyDataSetChanged();
    }

    public boolean isEditEnabled()
    {
        return editEnabled;
    }

    public static class ViewHolder
    {
        TextView stopName;
        ImageView buttonRemove;
    }
}