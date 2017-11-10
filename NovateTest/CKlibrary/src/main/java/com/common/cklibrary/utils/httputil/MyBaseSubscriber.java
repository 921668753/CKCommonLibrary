package com.common.cklibrary.utils.httputil;

import android.content.Context;

import com.tamic.novate.BaseSubscriber;

/**
 * Created by LIUYONGKUI726 on 2017-06-01.
 */

@SuppressWarnings("deprecation")
public abstract class MyBaseSubscriber<T> extends BaseSubscriber<T> {


    private Context context;

    public MyBaseSubscriber(Context context) {
        super(context);
        this.context = context;
    }

    public MyBaseSubscriber() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
    }
}
