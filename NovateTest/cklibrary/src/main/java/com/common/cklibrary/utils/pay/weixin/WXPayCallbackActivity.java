package com.common.cklibrary.utils.pay.weixin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.common.cklibrary.R;
import com.common.cklibrary.common.BaseActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 微信回调页面
 * Created by Administrator on 17/11/21.
 */
public class WXPayCallbackActivity extends BaseActivity implements IWXAPIEventHandler {


    @Override
    public void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wxpay_call_back);
    }

    @Override
    public void initData() {
        super.initData();
        if (WXPay.getInstance() != null) {
            WXPay.getInstance().getWXApi().handleIntent(getIntent(), this);
        } else {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (WXPay.getInstance() != null) {
            WXPay.getInstance().getWXApi().handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (WXPay.getInstance() != null) {
                if (baseResp.errStr != null) {
                    Log.e("wxpay", "errstr=" + baseResp.errStr);
                }
                WXPay.getInstance().onResp(baseResp.errCode);
                finish();
            }
        }
    }


}
