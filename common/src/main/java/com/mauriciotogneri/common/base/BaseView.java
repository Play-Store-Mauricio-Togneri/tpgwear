package com.mauriciotogneri.common.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseView<UI extends BaseUiContainer> implements BaseViewInterface<UI>
{
    private View view;
    protected UI ui;

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
    public void toast(int messageId)
    {
        toast(getContext().getString(messageId));
    }

    @Override
    public void toast(String message)
    {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}