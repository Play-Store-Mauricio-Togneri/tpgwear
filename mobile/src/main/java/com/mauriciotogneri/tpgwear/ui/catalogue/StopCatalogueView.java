package com.mauriciotogneri.tpgwear.ui.catalogue;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mauriciotogneri.common.api.tpg.json.Stop;
import com.mauriciotogneri.common.base.BaseUiContainer;
import com.mauriciotogneri.common.base.BaseView;
import com.mauriciotogneri.tpgwear.R;
import com.mauriciotogneri.tpgwear.adapters.StopCatalogueAdapter;
import com.mauriciotogneri.tpgwear.ui.catalogue.StopCatalogueView.UiContainer;

import java.util.List;

public class StopCatalogueView extends BaseView<UiContainer> implements StopCatalogueViewInterface<UiContainer>
{
    private StopCatalogueAdapter adapter;
    private boolean searchEnabled = false;

    @Override
    public void initialize(final StopCatalogueViewObserver observer)
    {
        ui.list.setVisibility(View.GONE);
        ui.progressBar.setVisibility(View.VISIBLE);

        ui.buttonSearch.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                observer.onSearchStop();
            }
        });

        adapter = new StopCatalogueAdapter(getContext());

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Stop stop = (Stop) parent.getItemAtPosition(position);
                observer.onStopSelected(stop);
            }
        });

        ui.toolbarSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
            {
            }

            @Override
            public void afterTextChanged(Editable arg0)
            {
            }
        });
    }

    @Override
    public void toggleSearch()
    {
        if (ui.progressBar.getVisibility() == View.GONE)
        {
            searchEnabled = !searchEnabled;

            if (searchEnabled)
            {
                ui.buttonSearch.setImageResource(R.drawable.ic_cancel);

                ui.toolbarSearch.setVisibility(View.VISIBLE);
                ui.toolbarSearch.setText("");
                ui.toolbarSearch.requestFocus();

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(ui.toolbarSearch, InputMethodManager.SHOW_IMPLICIT);

                ui.toolbarTitle.setVisibility(View.GONE);
            }
            else
            {
                ui.buttonSearch.setImageResource(R.drawable.ic_search);

                ui.toolbarSearch.setVisibility(View.GONE);
                ui.toolbarSearch.setText("");

                ui.toolbarTitle.setVisibility(View.VISIBLE);

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ui.toolbarSearch.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void displayData(List<Stop> stops)
    {
        ui.list.setVisibility(View.VISIBLE);
        ui.progressBar.setVisibility(View.GONE);

        adapter.setData(stops);
    }

    @Override
    public void refreshData()
    {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoading()
    {
        ui.progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getViewId()
    {
        return R.layout.screen_stop_catalogue;
    }

    @Override
    public UiContainer getUiContainer(BaseView baseView)
    {
        return new UiContainer(baseView);
    }

    public static class UiContainer extends BaseUiContainer
    {
        private final ListView list;
        private final ImageView buttonSearch;
        private final ProgressBar progressBar;
        private final TextView toolbarTitle;
        private final EditText toolbarSearch;

        public UiContainer(BaseView baseView)
        {
            super(baseView);

            this.list = (ListView) findViewById(R.id.list);
            this.buttonSearch = (ImageView) findViewById(R.id.button_search);
            this.progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            this.toolbarSearch = (EditText) findViewById(R.id.toolbar_search);
        }
    }
}