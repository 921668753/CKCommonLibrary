package com.ruitukeji.novate.download;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.common.ViewInject;
import com.ruitukeji.novate.R;
import com.ruitukeji.novate.statusbar.StatusBarActivity;

/**
 * 下载页面
 * Created by Admin on 2017/11/7.
 */

public class DownloadActivity extends BaseActivity implements DownloadContract.View {


    @BindView(id = R.id.tv_download, click = true)
    private TextView tv_download;

    @BindView(id = R.id.tv_statusBar, click = true)
    private TextView tv_statusBar;

    private String DOWNLOAD_URL = "http://imtt.dd.qq.com/16891/8C3E058EAFBFD4F1EFE0AAA815250716.apk?fsname=com.tencent.mobileqq_7.1.0_692.apk&csr=1bbd";

    @Override
    public void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_download);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new DownloadPresenter(this);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_download:
                ((DownloadContract.Presenter) mPresenter).downloadApp(DOWNLOAD_URL);
                break;
            case R.id.tv_statusBar:
                showActivity(aty, StatusBarActivity.class);
                break;
        }
    }


    @Override
    public void setPresenter(DownloadContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void getSuccess(int flag, String success) {
        dismissLoadingDialog();
        ViewInject.toast(success);
    }

    @Override
    public void errorMsg(int errorCode, String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }
}
