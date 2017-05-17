package com.ckcommon.common;

import android.os.Bundle;
import android.view.View;

import com.kymjs.rxvolley.RxVolley;

import cn.pedant.SweetAlert.SweetAlertDialog;


//import com.umeng.analytics.MobclickAgent;

//import org.kymjs.kjframe.KJActivity;

/**
 * 公用的父Activity
 * 防止除向其他共用时增加
 * Created by ruitu on 2016/9/13.
 */

public abstract class BaseActivity extends KJActivity implements LoadingDialogView {
    public Object mPresenter;
    private SweetAlertDialog mLoadingDialog;

    /**
     * 解决framenet重影
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //  super.onSaveInstanceState(outState);
    }

//    /**
//     * session的统计
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //   MobclickAgent.onResume(this);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        RxVolley.getRequestQueue().cancelAll(KJActivityStack.create().getClass().getName());
        //    MobclickAgent.onPause(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void showLoadingDialog(String title,int color) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            mLoadingDialog.getProgressHelper().setBarColor(getResources().getColor(color));
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setTitleText(title);
        }
        mLoadingDialog.show();
        ((View) mLoadingDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).getParent()).setVisibility(View.GONE);
    }

    @Override
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }
}
