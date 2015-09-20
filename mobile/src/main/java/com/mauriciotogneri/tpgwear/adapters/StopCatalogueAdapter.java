package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StopCatalogueAdapter.ViewHolder;

import java.text.Collator;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StopCatalogueAdapter extends BaseAdapter<Stop, ViewHolder> implements Filterable
{
    private List<Stop> originalData = new ArrayList<>();
    private List<Stop> filteredData = new ArrayList<>();

    public StopCatalogueAdapter(Context context)
    {
        super(context, R.layout.row_stop_catalogue, new ArrayList<Stop>());
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Stop stop)
    {
        viewHolder.stopName.setText(stop.stopName);

        if (stop.favorite)
        {
            viewHolder.isFavorite.setImageResource(R.drawable.ic_star_on);
        }
        else
        {
            viewHolder.isFavorite.setImageResource(R.drawable.ic_star_off);
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

    @Override
    public int getCount()
    {
        return filteredData.size();
    }

    @Override
    public Stop getItem(int position)
    {
        return filteredData.get(position);
    }

    @Override
    public void setData(List<Stop> list)
    {
        super.setData(list);

        originalData = list;
        filteredData = list;
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                filteredData = (List<Stop>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                FilterResults results = new FilterResults();
                List<Stop> filtered = new ArrayList<>();

                Collator collator = Collator.getInstance(Locale.FRENCH);
                collator.setStrength(Collator.NO_DECOMPOSITION);
                String query = removeDiacriticalMarks(constraint.toString().toLowerCase());

                for (Stop stop : originalData)
                {
                    String stopName = removeDiacriticalMarks(stop.stopName.toLowerCase());

                    if (stopName.contains(query) || TextUtils.isEmpty(query))
                    {
                        filtered.add(stop);
                    }
                }

                results.count = filtered.size();
                results.values = filtered;

                return results;
            }

            private String removeDiacriticalMarks(String string)
            {
                return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            }
        };
    }

    public static class ViewHolder
    {
        TextView stopName;
        ImageView isFavorite;
    }
}