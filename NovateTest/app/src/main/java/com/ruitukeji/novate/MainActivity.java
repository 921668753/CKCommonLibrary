package com.ruitukeji.novate;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.common.GlideImageLoader;
import com.common.cklibrary.common.ViewInject;
import com.common.cklibrary.utils.JsonUtil;
import com.common.cklibrary.utils.rx.MsgEvent;
import com.common.cklibrary.utils.rx.RxBus;
import com.kymjs.common.Log;
import com.ruitukeji.novate.download.DownloadActivity;
import com.ruitukeji.novate.entity.AppConfigBean;
import com.ruitukeji.novate.rxbus.RxBusActivity;

import rx.functions.Action1;

public class MainActivity extends BaseActivity implements MainContract.View {


    @BindView(id = R.id.iv_head, click = true)
    private ImageView iv_head;

    @BindView(id = R.id.tv_context, click = true)
    private TextView tv_context;

    @BindView(id = R.id.tv_webView, click = true)
    private TextView tv_webView;

    @BindView(id = R.id.tv_rxBus, click = true)
    private TextView tv_rxBus;

    @BindView(id = R.id.tv_rxBus1, click = true)
    private TextView tv_rxBus1;

    @BindView(id = R.id.tv_rxBusTest)
    private TextView tv_rxBusTest;


    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new MainPresenter(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        GlideImageLoader.glideOrdinaryLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", iv_head);
        tv_context.setText("http://imtt.dd.qq.com/16891/8C3E058EAFBFD4F1EFE0AAA815250716.apk?fsname=com.tencent.mobileqq_7.1.0_692.apk&csr=1bbd");
    }


    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.iv_head:
                showLoadingDialog("加载中。。。");
                ((MainContract.Presenter) mPresenter).getAppConfig();
                break;
            case R.id.tv_context:
                ((MainContract.Presenter) mPresenter).downloadApp(tv_context.getText().toString().trim());
                break;
            case R.id.tv_webView:
                showActivity(aty, DownloadActivity.class);
                break;
            case R.id.tv_rxBus:
                showActivity(aty, RxBusActivity.class);
                break;
            case R.id.tv_rxBus1:
                RxBus.getInstance().post(new MsgEvent(11, "MainActivityEvent"));
                break;
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(int flag, String success) {
        if (flag == 0) {
            AppConfigBean appConfigBean = (AppConfigBean) JsonUtil.getInstance().json2Obj(success, AppConfigBean.class);
            String updateAppUrl = appConfigBean.getResult().getLastApkUrl();
            tv_context.setText(updateAppUrl);
            Log.d("test", success);
        } else if (flag == 1) {
            ViewInject.toast(success);
        }
        Log.d("test", success);
        dismissLoadingDialog();
    }

    @Override
    public void errorMsg(int errCode, String msg) {
        dismissLoadingDialog();
        Log.d("test", msg);
  //      ViewInject.toast(msg);
    }


    /**
     * 在接收消息的时候，选择性接收消息：
     */
    @Override
    public void callMsgEvent(MsgEvent msgEvent) {
        super.callMsgEvent(msgEvent);
        if (msgEvent.getType() == 11) {
            tv_rxBusTest.setText(msgEvent.getMsg());
            Log.d("test", msgEvent.getMsg());
            ((MainContract.Presenter) mPresenter).getAppConfig();
        }
    }
}
