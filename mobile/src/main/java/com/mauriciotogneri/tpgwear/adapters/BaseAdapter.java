package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public abstract class BaseAdapter<T, V> extends ArrayAdapter<T>
{
    private final LayoutInflater inflater;
    private final int resourceId;

    public BaseAdapter(Context context, int resourceId, List<T> list)
    {
        super(context, android.R.layout.simple_list_item_1, list);

        this.inflater = LayoutInflater.from(context);
        this.resourceId = resourceId;
    }

    protected abstract void fillView(V viewHolder, T item);

    protected abstract V getViewHolder(View view);

    public void setData(List<T> list)
    {
        clear();
        addAll(list);
        notifyDataSetChanged();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent)
    {
        V viewHolder;
        View rowView = convertView;

        if (rowView == null)
        {
            rowView = inflater.inflate(resourceId, parent, false);
            viewHolder = getViewHolder(rowView);
            rowView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (V) rowView.getTag();
        }

        T item = getItem(position);

        fillView(viewHolder, item);

        return rowView;
    }
}