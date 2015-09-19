package com.mauriciotogneri.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseActivity<V extends BaseViewInterface> extends Activity
{
    protected V view;

    @Override
    public final void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.view = getViewInstance();
        ViewGroup container = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        View layout = this.view.init(getLayoutInflater(), container);
        setContentView(layout);

        initialize();
    }

    protected abstract V getViewInstance();

    protected void initialize()
    {
    }

    protected void toast(final String message)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                view.toast(message);
            }
        });
    }
}