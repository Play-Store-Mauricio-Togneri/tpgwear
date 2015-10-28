package com.mauriciotogneri.tpgwear.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.Line;
import com.mauriciotogneri.common.api.tpg.Stop;
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

        List<Line> lines = stop.getLines();
        int size = lines.size();

        fillLineBubble(viewHolder.line1, 0, size, lines);
        fillLineBubble(viewHolder.line2, 1, size, lines);
        fillLineBubble(viewHolder.line3, 2, size, lines);
        fillLineBubble(viewHolder.line4, 3, size, lines);
        fillLineBubble(viewHolder.line5, 4, size, lines);

        if (size > 6)
        {
            viewHolder.line6.setText("···");

            GradientDrawable background = (GradientDrawable) viewHolder.line6.getBackground();
            background.setStroke(6, Color.parseColor("#FFFFFFFF"));

            viewHolder.line6.setVisibility(View.VISIBLE);
        }
        else
        {
            fillLineBubble(viewHolder.line6, 5, size, lines);
        }
    }

    private void fillLineBubble(TextView textView, int index, int size, List<Line> lines)
    {
        if (size > index)
        {
            Line line = lines.get(index);

            textView.setText(line.lineCode);

            try
            {
                GradientDrawable background = (GradientDrawable) textView.getBackground();
                background.setStroke(6, Color.parseColor("#" + line.color));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            textView.setVisibility(View.VISIBLE);
        }
        else
        {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewHolder getViewHolder(View view)
    {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.isFavorite = (ImageView) view.findViewById(R.id.is_favorite);
        viewHolder.stopName = (TextView) view.findViewById(R.id.stop_name);
        viewHolder.line1 = (TextView) view.findViewById(R.id.line_1);
        viewHolder.line2 = (TextView) view.findViewById(R.id.line_2);
        viewHolder.line3 = (TextView) view.findViewById(R.id.line_3);
        viewHolder.line4 = (TextView) view.findViewById(R.id.line_4);
        viewHolder.line5 = (TextView) view.findViewById(R.id.line_5);
        viewHolder.line6 = (TextView) view.findViewById(R.id.line_6);

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
        ImageView isFavorite;
        TextView stopName;
        TextView line1;
        TextView line2;
        TextView line3;
        TextView line4;
        TextView line5;
        TextView line6;
    }
}