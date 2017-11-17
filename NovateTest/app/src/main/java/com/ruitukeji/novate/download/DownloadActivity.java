package com.ruitukeji.novate.download;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.common.ViewInject;
import com.kymjs.common.Log;
import com.ruitukeji.novate.R;

/**
 * 下载页面
 * Created by Admin on 2017/11/7.
 */

public class DownloadActivity extends BaseActivity implements DownloadContract.View {


    @BindView(id = R.id.tv_download, click = true)
    private TextView tv_download;

    private String DOWNLOAD_URL = "http://imtt.dd.qq.com/16891/E313866A4E996564A4F38766505D4D2B.apk?fsname=com.snda.wifilocating_4.2.30_3160.apk&csr=1bbd";

    @Override
    public void setRootView() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
