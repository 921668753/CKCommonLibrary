package com.ckcommon.common;

import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 公用的父Fragment
 * 防止除向其他共用时增加
 * Created by ruitu on 2016/9/14.
 */

public abstract class BaseFragment extends KJFragment implements LoadingDialogView {

    public Object mPresenter;
    private SweetAlertDialog mLoadingDialog;

    @SuppressWarnings("deprecation")
    @Override
    public void showLoadingDialog(String title,int color) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
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
    public void onPause() {
        super.onPause();
        //   RxVolley.getRequestQueue().cancelAll(KJActivityStack.create().getClass().getName());
        //    MobclickAgent.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }
}
