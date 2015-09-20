package com.mauriciotogneri.common.base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseView<UI extends BaseUiContainer> implements BaseViewInterface<UI>
{
    private View view;
    protected UI ui;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public final View init(LayoutInflater inflater, ViewGroup container)
    {
        this.view = inflater.inflate(getViewId(), container, false);
        this.ui = getUiContainer(this);

        return this.view;
    }

    @Override
    public View findViewById(@IdRes int viewId)
    {
        return view.findViewById(viewId);
    }

    @Override
    public Context getContext()
    {
        return this.view.getContext();
    }

    @Override
    public void onMainThread(Runnable runnable)
    {
        handler.post(runnable);
    }

    @Override
    public void toast(int messageId)
    {
        toast(getContext().getString(messageId));
    }

    @Override
    public void toast(final String message)
    {
        Log.e("ERROR", message);

        onMainThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}