package com.ruitukeji.novate.download;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.utils.httputil.ResponseChangListener;
import com.kymjs.common.Log;
import com.othershe.dutil.DUtil;
import com.othershe.dutil.callback.DownloadCallback;
import com.othershe.dutil.download.DownloadManger;
import com.othershe.dutil.utils.Utils;
import com.ruitukeji.novate.R;
import com.ruitukeji.novate.retrofit.RequestClient;

import java.io.File;


/**
 * Created by ruitu on 2016/9/24.
 */

public class SingleTaskPresenter implements SingleTaskContract.Presenter {
    private SingleTaskContract.View mView;

    public SingleTaskPresenter(SingleTaskContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void singleTaskDownloadApp(String updateAppUrl, DownloadManger downloadManger, TextView mTip, TextView mProgress, ProgressBar progressBar) {
        String name = "消消乐";

        DownloadCallback callback = new DownloadCallback() {

            @Override
            public void onStart(long currentSize, long totalSize, float progress) {
                mTip.setText(name + "：准备下载中...");
                mView.showLoadingDialog("准备下载中...");
                progressBar.setProgress((int) progress);
                mProgress.setText(Utils.formatSize(currentSize) + " / " + Utils.formatSize(totalSize) + "--------" + progress + "%");
            }

            @Override
            public void onProgress(long currentSize, long totalSize, float progress) {
                mTip.setText(name + "：下载中...");
                mView.showLoadingDialog("下载中" + progress + "%");
                progressBar.setProgress((int) progress);
                mProgress.setText(Utils.formatSize(currentSize) + " / " + Utils.formatSize(totalSize) + "--------" + progress + "%");
            }

            @Override
            public void onPause() {
                mTip.setText(name + "：暂停中...");
            }

            @Override
            public void onCancel() {
                mTip.setText(name + "：已取消...");
            }

            @Override
            public void onFinish(File file) {
                mTip.setText(name + "：下载完成...");
                mView.getSuccess(0, file.getAbsolutePath());
            }

            @Override
            public void onWait() {

            }

            @Override
            public void onError(String error) {
                mTip.setText(name + "：下载出错..." + error);
                mView.errorMsg(0, error);
            }
        };
        downloadManger.start(callback);
    }
}
