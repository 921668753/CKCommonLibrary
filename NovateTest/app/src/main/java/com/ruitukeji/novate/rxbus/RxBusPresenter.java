package com.ruitukeji.novate.rxbus;

import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.utils.httputil.NovateRestponse;
import com.common.cklibrary.utils.httputil.ResponseChangListener;
import com.common.cklibrary.utils.httputil.ResponseListener;
import com.kymjs.common.Log;
import com.ruitukeji.novate.R;
import com.ruitukeji.novate.retrofit.RequestClient;

import java.util.Map;

/**
 * Created by ruitu on 2016/9/24.
 */

public class RxBusPresenter implements RxBusContract.Presenter {
    private RxBusContract.View mView;

    public RxBusPresenter(RxBusContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getAppConfig() {
      //  mView.showLoadingDialog("加载中。。。");
        Map<String, Object> parameters = NovateRestponse.requestParameters();
        RequestClient.getAppConfig1(parameters, new ResponseListener<String>() {
            @Override
            public void onSuccess(String response) {
                mView.getSuccess(0, response);
            }

            @Override
            public void onFailure(int errCode, String msg) {
                mView.errorMsg(errCode, msg);
            }
        });
    }

    @Override
    public void downloadApp(String updateAppUrl) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download));
        RequestClient.downloadApp(updateAppUrl, new ResponseChangListener<String>() {
            @Override
            public void onChanged(String key, int progress, long fileSizeDownloaded, long totalSize) {
//                String size = MathUtil.keepZero(((double) fileSizeDownloaded) * 100 / totalSize) + "%";
                Log.d("tag", progress + "");
                String size = progress + "%";
                mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download) + size);
            }

            @Override
            public void onSuccess(String response) {
                mView.getSuccess(1, response + KJActivityStack.create().topActivity().getString(R.string.app_name) + ".apk");
            }

            @Override
            public void onFailure(int errCode, String msg) {
                mView.errorMsg(errCode, msg);
            }
        });
    }
}
