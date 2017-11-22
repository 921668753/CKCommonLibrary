package com.common.cklibrary.utils.pay.weixin;

import android.content.Context;
import android.text.TextUtils;

import com.common.cklibrary.R;
import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.common.ViewInject;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信支付
 * Created by Administrator on 17/11/21.
 */
public class WXPay {

    private static WXPay mWXPay;
    private IWXAPI mWXApi;

    private WXPayResultCallBack mCallback;

    public interface WXPayResultCallBack {

        void onSuccess(); //支付成功

    }

    public WXPay(Context context, String wx_appid) {
        mWXApi = WXAPIFactory.createWXAPI(context, null);
        mWXApi.registerApp(wx_appid);
    }

    public static void init(Context context, String wx_appid) {
        if (mWXPay == null) {
            mWXPay = new WXPay(context, wx_appid);
        }
    }

    public static WXPay getInstance() {
        return mWXPay;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    /**
     * 发起微信支付
     */
    public void doPay(String pay_param, WXPayResultCallBack callback) {

        mCallback = callback;

        if (!check()) {
            ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.weChatLow));
            return;
        }

        JSONObject param = null;
        try {
            param = new JSONObject(pay_param);
        } catch (JSONException e) {
            e.printStackTrace();
            ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.payment_error));
            return;
        }

        if (TextUtils.isEmpty(param.optString("appid")) || TextUtils.isEmpty(param.optString("partnerid"))
                || TextUtils.isEmpty(param.optString("prepayid")) || TextUtils.isEmpty(param.optString("package")) ||
                TextUtils.isEmpty(param.optString("noncestr")) || TextUtils.isEmpty(param.optString("timestamp")) ||
                TextUtils.isEmpty(param.optString("sign"))) {
            ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.payment_error));
            return;
        }

        PayReq req = new PayReq();
        req.appId = param.optString("appid");
        req.partnerId = param.optString("partnerid");
        req.prepayId = param.optString("prepayid");
        req.packageValue = param.optString("package");
        req.nonceStr = param.optString("noncestr");
        req.timeStamp = param.optString("timestamp");
        req.sign = param.optString("sign");
        mWXApi.sendReq(req);
    }

    //支付回调响应
    public void onResp(int error_code) {

        if (mCallback == null) {
            ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.operation_error));
            return;
        }

        switch (error_code) {
            case BaseResp.ErrCode.ERR_OK:
                //支付成功
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.payment_succeed));
                mCallback.onSuccess();
                break;
            case BaseResp.ErrCode.ERR_COMM:
                //支付错误
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.pay_error));
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //取消支付
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.pay_cancel));
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //认证被否决
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.authentication_rejected));
                break;
            case BaseResp.ErrCode.ERR_SENT_FAILED:
                //发送失败
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.send_failure));
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                //不支持错误
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.unsupported_error));
                break;
            default:
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.operation_error));
                break;
        }
        mCallback = null;
    }

    //检测是否支持微信支付
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
}
