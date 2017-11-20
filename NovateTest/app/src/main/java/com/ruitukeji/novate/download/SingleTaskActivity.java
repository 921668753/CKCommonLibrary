package com.ruitukeji.novate.download;

import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.common.ViewInject;
import com.common.cklibrary.utils.FileNewUtil;
import com.othershe.dutil.DUtil;
import com.othershe.dutil.download.DownloadManger;
import com.ruitukeji.novate.BuildConfig;
import com.ruitukeji.novate.R;

/**
 * 单任务下载
 */
public class SingleTaskActivity extends BaseActivity implements SingleTaskContract.View {

    /**
     * http://1.198.5.23/imtt.dd.qq.com/16891/B8723A0DB2F2702C04D801D9FD19822C.apk //阴阳师
     * http://1.82.215.170/imtt.dd.qq.com/16891/85B6221DE84C466310575D9FBCA453A8.apk  //天天酷跑
     * http://1.198.5.22/imtt.dd.qq.com/16891/8EEC7D8996760973B5CEA15ECA1700E3.apk  //消消乐
     */

    @BindView(id = R.id.tip)
    private TextView mTip;

    @BindView(id = R.id.progress1)
    private TextView mProgress;

    @BindView(id = R.id.pause, click = true)
    private TextView mPause;

    @BindView(id = R.id.resume, click = true)
    private TextView mResume;

    @BindView(id = R.id.cancel, click = true)
    private TextView mCancel;

    @BindView(id = R.id.restart, click = true)
    private TextView mRestart;

    @BindView(id = R.id.progress_bar, click = true)
    private ProgressBar progressBar;

    private String url;

    private DownloadManger downloadManger;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_single_task);
    }

    @Override
    public void initData() {
        super.initData();
        String name = "消消乐";
        url = "http://imtt.dd.qq.com/16891/E313866A4E996564A4F38766505D4D2B.apk?fsname=com.snda.wifilocating_4.2.30_3160.apk&csr=1bbd";
        mPresenter = new SingleTaskPresenter(this);
        downloadManger = DUtil.init(KJActivityStack.create().topActivity())
                .url(url)
                .path(Environment.getExternalStorageDirectory() + "/DUtil/")
                .name(name + ".apk")
                .childTaskCount(3)
                .build();
    }

    @Override
    public void initWidget() {
        super.initWidget();
        ((SingleTaskContract.Presenter) mPresenter).singleTaskDownloadApp(url, downloadManger, mTip, mProgress, progressBar);
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.pause:
                downloadManger.pause(url);
                break;
            case R.id.resume:
                downloadManger.resume(url);
                break;
            case R.id.cancel:
                downloadManger.cancel(url);
                break;
            case R.id.restart:
                downloadManger.restart(url);
                break;
        }
    }

    @Override
    public void setPresenter(SingleTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(int flag, String success) {
        dismissLoadingDialog();
        FileNewUtil.installApkFile(this, BuildConfig.APPLICATION_ID, success);
    }

    @Override
    public void errorMsg(int errorCode, String msg) {
        dismissLoadingDialog();
        ViewInject.toast(msg);
    }


    @Override
    protected void onDestroy() {
        downloadManger.destroy(url);
        super.onDestroy();
    }

}
