package com.ruitukeji.novate.download;

import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.common.ViewInject;
import com.ruitukeji.novate.R;

/**
 * 下载页面
 * Created by Admin on 2017/11/7.
 */

public class DownloadActivity extends BaseActivity implements DownloadContract.View {


    @BindView(id = R.id.tv_download, click = true)
    private TextView tv_download;

    private static final String DOWNLOAD_URL = "http://imtt.dd.qq.com/16891/8C3E058EAFBFD4F1EFE0AAA815250716.apk?fsname=com.tencent.mobileqq_7.1.0_692.apk&csr=1bbd";

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_download);
    }

    @Override
    public void initData() {
        super.initData();
        Aria.download(this).register();
        mPresenter = new DownloadPresenter(this);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_download:
                Aria.download(this)
                        .load(DOWNLOAD_URL)
                        .setDownloadPath(Environment.getExternalStorageDirectory().getPath() + "/放置江湖.apk")
                        .start();
                //   ((DownloadContract.Presenter) mPresenter).downloadApp("http://imtt.dd.qq.com/16891/8C3E058EAFBFD4F1EFE0AAA815250716.apk?fsname=com.tencent.mobileqq_7.1.0_692.apk&csr=1bbd");
                break;
        }
    }


    @Override
    public void setPresenter(DownloadContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 预处理的注解，在任务为开始前回调（一般在此处预处理UI界面）
     *
     * @param task
     */
    @Download.onPre
    void onPre(DownloadTask task) {
    }

    /**
     * 任务开始时的注解，新任务开始时进行回调
     *
     * @param task
     */
    @Download.onTaskStart
    void taskStart(DownloadTask task) {
        ViewInject.toast("新任务开始时进行回调");
    }

    /**
     * 任务执行时的注解，任务正在执行时进行回调
     *
     * @param task
     */
    @Download.onTaskRunning
    void running(DownloadTask task) {
        showLoadingDialog("下载中" + task.getConvertSpeed() + "%");
    }

    /**
     * 任务停止时的注解，任务停止时进行回调
     *
     * @param task
     */
    @Download.onTaskStop
    void taskStop(DownloadTask task) {
        ViewInject.toast("任务停止时进行回调");
    }

    /**
     * 任务完成时的注解，任务完成时进行回调
     *
     * @param task
     */
    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        ViewInject.toast("任务完成时进行回调");
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
        Aria.download(this).unRegister();
    }
}
