package com.ruitukeji.novate.download;

import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.utils.httputil.ResponseChangListener;
import com.kymjs.common.Log;
import com.ruitukeji.novate.R;
import com.ruitukeji.novate.retrofit.RequestClient;


/**
 * Created by ruitu on 2016/9/24.
 */

public class DownloadPresenter implements DownloadContract.Presenter {
    private DownloadContract.View mView;

    public DownloadPresenter(DownloadContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void downloadApp(String updateAppUrl) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download));
        RequestClient.downloadRxApp(updateAppUrl, new ResponseChangListener<String>() {
            @Override
            public void onChanged(String key, int progress, long fileSizeDownloaded, long totalSize) {
//                String size = MathUtil.keepZero(((double) fileSizeDownloaded) * 100 / totalSize) + "%";
                        Log.d("tag", progress + "");
                        String size = progress + "%";
                        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download) + size);
            }

            @Override
            public void onSuccess(String response) {
                mView.getSuccess(0, response + KJActivityStack.create().topActivity().getString(R.string.app_name) + ".apk");
            }

            @Override
            public void onFailure(int errCode, String msg) {
                KJActivityStack.create().topActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.errorMsg(errCode, msg);
                    }
                });
            }
        });
    }
}
