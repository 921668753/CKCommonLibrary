package com.ruitukeji.novate.rxbus;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.common.ViewInject;
import com.common.cklibrary.utils.JsonUtil;
import com.common.cklibrary.utils.rx.MsgEvent;
import com.common.cklibrary.utils.rx.RxBus;
import com.kymjs.common.Log;
import com.ruitukeji.novate.R;
import com.ruitukeji.novate.entity.AppConfigBean;

import java.util.HashMap;
import java.util.Map;

/**
 * 类似于EventBus
 * Created by Admin on 2017/11/8.
 */

public class RxBusActivity extends BaseActivity implements RxBusContract.View {


    @BindView(id = R.id.tv_send, click = true)
    private TextView tv_send;

    @BindView(id = R.id.tv_mapList, click = true)
    private TextView tv_mapList;

    @BindView(id = R.id.tv_accordingTo)
    private TextView tv_accordingTo;
    Map<String, Object> map = null;

    @Override
    public void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rxbus);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter = new RxBusPresenter(this);
        map = new HashMap<>();
        map.put("tag", 1);
        Log.d("test", "map=" + map.size() + JsonUtil.getInstance().obj2JsonString(map));
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_send:
                /**
                 * 发送消息
                 */
                RxBus.getInstance().post(new MsgEvent(11, "RxBusActivityEvent"));
                break;

            case R.id.tv_mapList:
                map.put("tag", 2);
                Log.d("test", "map=" + map.size() + JsonUtil.getInstance().obj2JsonString(map));
                map.put("tag", 3);
                Log.d("test", "map=" + map.size() + JsonUtil.getInstance().obj2JsonString(map));
                break;
        }
    }

    /**
     * 在接收消息的时候，选择性接收消息：
     */
    @Override
    public void callMsgEvent(MsgEvent msgEvent) {
        super.callMsgEvent(msgEvent);
        if (msgEvent.getType() == 11) {
            tv_accordingTo.setText(msgEvent.getMsg());
            Log.d("test", msgEvent.getMsg());
            showLoadingDialog("11111");
            ((RxBusContract.Presenter) mPresenter).getAppConfig();
        }
    }

    @Override
    public void setPresenter(RxBusContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(int flag, String success) {
        if (flag == 0) {
            AppConfigBean appConfigBean = (AppConfigBean) JsonUtil.getInstance().json2Obj(success, AppConfigBean.class);
            String updateAppUrl = appConfigBean.getResult().getLastApkUrl();
            //    tv_context.setText(updateAppUrl);
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
        Log.d("test", this.getClass().getName() + msg);
        ViewInject.toast(msg);
    }

}
