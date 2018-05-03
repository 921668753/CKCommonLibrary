package com.common.cklibrary.utils.pay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.common.cklibrary.R;
import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.common.ViewInject;
import com.kymjs.common.StringUtils;

import java.util.Map;

/**
 * 支付宝支付
 * Created Administrator tsy on 17/11/21
 */
public class Alipay {
    private PayTask mPayTask;
    private AlipayResultCallBack mCallback;

    private static final int SDK_PAY_FLAG = 1;

//    public static final int ERROR_RESULT = 1;   //支付结果解析错误
//    public static final int ERROR_PAY = 2;  //支付失败
//    public static final int ERROR_NETWORK = 3;  //网络连接错误

    public interface AlipayResultCallBack {
        void onSuccess(); //支付成功

//        void onDealing();    //正在处理中 小概率事件 此时以验证服务端异步通知结果为准
//
//        void onError(int error_code);   //支付失败
//
//        void onCancel();    //支付取消
    }

    public Alipay(Context context, AlipayResultCallBack callback) {
        mCallback = callback;
        // 构造PayTask 对象
        mPayTask = new PayTask((Activity) context);
    }

    /**
     * 支付
     *
     * @param mParams 支付参数
     */
    public void doPay(final String mParams) {
        Log.d("payInfo", mParams);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 调用支付接口
                Map<String, String> result = mPayTask.payV2(mParams, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            if (msg.what != SDK_PAY_FLAG) {
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_system_exception));
                return;
            }
            String result = msg.obj.toString();
            Log.d("payInfo", result);
            if (StringUtils.isEmpty(result)) {
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_system_exception));
                return;
            }
            @SuppressWarnings("unchecked")
            AlipayResult payResult = new AlipayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            if (StringUtils.isEmpty(resultStatus)) {
                ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_system_exception));
                return;
            }
            switch (resultStatus) {
                case "9000":
                    // 操作成功
                    mCallback.onSuccess();
                    break;
                case "4000":
                    // 系统异常
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_system_exception));
                    break;
                case "4001":
                    // 数据格式不正确
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_system_exception));
                    break;
                case "4003":
                    // 该用户绑定的支付宝账户被冻结或不允许支付
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_account_exception));
                    break;
                case "4004":
                    // 该用户已解除绑定
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_has_unbound));
                    break;
                case "4005":
                    // 绑定失败或没有绑定
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_system_exception));
                    break;
                case "4006":
                    // 订单支付失败
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_order_error));
                    break;
                case "4010":
                    // 重新绑定账户
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_system_exception));
                    break;
                case "6000":
                    // 支付服务正在进行升级操作
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_system_exception));
                    break;
                case "6001":
                    // 用户中途取消支付操作
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.pay_cancel));
                    break;
                case "6002":
                    // 网络连接异常
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.pay_network));
                    break;
                case "7001":
                    // 网页支付失败
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_order_error));
                    break;
                case "8000":
                    // 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.alipay_system_exception));
                    break;
                default:
                    ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.operation_error));
                    break;
            }
        }
    };

}
