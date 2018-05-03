package com.common.cklibrary.common;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;


import com.common.cklibrary.R;
import com.common.cklibrary.utils.rx.MsgEvent;
import com.common.cklibrary.utils.rx.RxBus;
import com.common.cklibrary.utils.rx.RxManager;
import com.tamic.novate.RxApiManager;


import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscription;
import rx.functions.Action1;


/**
 * 公用的父Activity
 * 防止除向其他共用时增加
 * Created by ruitu on 2016/9/13.
 */

public abstract class BaseActivity extends KJActivity implements LoadingDialogView {
    public Object mPresenter;
    public Subscription subscription = null;
    private SweetAlertDialog mLoadingDialog = null;

    /**
     * 解决framenet重影
     *
     * @param outState
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    /**
     * 必须此处创建订阅者 Subscription subscription
     */
    @Override
    public void initData() {
        super.initData();
        subscription = RxBus.getInstance().register(MsgEvent.class).subscribe(new Action1<MsgEvent>() {
            @Override
            public void call(MsgEvent msgEvent) {
                callMsgEvent(msgEvent);
            }
        });
    }

//    /**
//     * session的统计
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //   MobclickAgent.onResume(this);
//    }


    @SuppressWarnings("deprecation")
    @Override
    public void showLoadingDialog(String title) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(KJActivityStack.create().topActivity(), SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper().setBarColor(Color.BLACK);
            mLoadingDialog.setCancelable(false);
        }
        mLoadingDialog.setTitleText(title);
        mLoadingDialog.show();
        ((View) mLoadingDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).getParent()).setVisibility(View.GONE);
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            try {
                mLoadingDialog.dismiss();
            } catch (Exception e) {
                mLoadingDialog = null;
            }
        }
        mLoadingDialog = null;
    }

    @Override
    public void initWidget() {
        super.initWidget();
        if (subscription != null && !subscription.isUnsubscribed()) {
            RxManager.get().add(this.getClass().getName(), subscription);
        }
    }


    public void callMsgEvent(MsgEvent msgEvent) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        dismissLoadingDialog();
        //    MobclickAgent.onPause(this);
    }

    /**
     * 页面销毁时取消订阅，防止内存溢出  Subscription subscription
     */
    @Override
    protected void onDestroy() {
        RxApiManager.get().cancel(KJActivityStack.create().topActivity().getClass().getName());
        RxManager.get().cancel(this.getClass().getName());
        super.onDestroy();
        subscription = null;
        mLoadingDialog = null;
        mPresenter = null;
    }
}
