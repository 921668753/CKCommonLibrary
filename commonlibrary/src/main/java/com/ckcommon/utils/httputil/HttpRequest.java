package com.ckcommon.utils.httputil;

import com.ckcommon.common.KJActivityStack;
import com.ckcommon.utils.JsonUtil;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;

//import com.kymjs.rxvolley.client.ProgressListener;

/**
 * Created by Administrator on 2017/3/8.
 */

public class HttpRequest {

    /**
     * 图片保存路径存放的文件名
     */
    public static int SUCCESS = 1;


    public static RxVolley.Builder builder = new RxVolley.Builder();


    public static void requestHttp(String url, int httpMethod, int contentType, HttpParams params, boolean isCache, final ResponseListener responseListener) {
        //http请求的回调，内置了很多方法，详细请查看源码
//包括在异步响应的onSuccessInAsync():注不能做UI操作
//网络请求成功时的回调onSuccess()
//网络请求失败时的回调onFailure():例如无网络，服务器异常等
        HttpCallback callback = new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                doSuccess(t, responseListener);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                doFailure(strMsg, responseListener);
            }
        };
        builder.url(url) //接口地址
                //请求类型，如果不加，默认为 GET 可选项：
                //POST/PUT/DELETE/HEAD/OPTIONS/TRACE/PATCH
                .httpMethod(httpMethod)
                //设置缓存时间: 默认是 get 请求 5 分钟, post 请求不缓存
                .cacheTime(6)
                //内容参数传递形式，如果不加，默认为 FORM 表单提交，可选项 JSON 内容
                .contentType(contentType)
                .params(params) //上文创建的HttpParams请求参数集
                //是否缓存，默认是 get 请求 5 缓存分钟, post 请求不缓存
                .shouldCache(isCache)
                .setTag(KJActivityStack.create().getClass().getName())
                //    .progressListener(listener) //上传进度
                .callback(callback) //响应回调
                .encoding("UTF-8") //编码格式，默认为utf-8
                .doTask();  //执行请求操作
    }

    /**
     * get请求
     *
     * @param url
     * 请求地址
     * @param params
     * 请求参数
     * @param responseListener
     * 回调监听
     */
    public static void requestGetHttp(String url, HttpParams params, ResponseListener responseListener) {
        requestHttp(url, RxVolley.Method.GET, RxVolley.ContentType.FORM, params, true, responseListener);
    }

    /**
     * postjosn请求
     *
     * @param url
     * 请求地址
     * @param params
     * 请求参数
     * @param responseListener
     * 回调监听
     */
    public static void requestPostHttp(String url, HttpParams params, ResponseListener responseListener) {
        requestHttp(url, RxVolley.Method.GET, RxVolley.ContentType.JSON, params, true, responseListener);
    }

    /**
     * PUTjosn请求
     *
     * @param url
     * 请求地址
     * @param params
     * 请求参数
     * @param responseListener
     * 回调监听
     */
    public static void requestPutHttp(String url, HttpParams params, ResponseListener responseListener) {
        requestHttp(url, RxVolley.Method.PUT, RxVolley.ContentType.FORM, params, true, responseListener);
    }

    /**
     * DELETEjosn请求
     *
     * @param url
     * 请求地址
     * @param params
     * 请求参数
     * @param responseListener
     * 回调监听
     */
    public static void requestDeleteHttp(String url, HttpParams params, ResponseListener responseListener) {
        requestHttp(url, RxVolley.Method.DELETE, RxVolley.ContentType.FORM, params, true, responseListener);
    }


    /**
     *  * 网络请求成功
     *
     * @param s
     * 返回参数
     * @param listener
     * 回调监听
     * @return
     * 返回参数
     */
    @SuppressWarnings("unchecked")
    public static boolean doSuccess(String s, ResponseListener listener) {
        BaseResult baseResult = (BaseResult) JsonUtil.getInstance().json2Obj(s, BaseResult.class);
        if (baseResult.getCode() != SUCCESS) {
            listener.onFailure(baseResult.getMessage());
            return false;
        }
        listener.onSuccess(s);
        return true;
    }

    /**
     * 接口返回失败信息处理
     *
     * @param errorMsg
     * 错误信息
     * @param listener
     * 回调监听
     */
    public static void doFailure(String errorMsg, ResponseListener listener) {
        if (errorMsg == null) {
            listener.onFailure("服务器返回数据错误");
        } else if (errorMsg.equals("socket timeout")) {
            listener.onFailure("连接超时");
        } else if (errorMsg.equals("Bad URL ")) {
            listener.onFailure("URL错误");
        } else if (errorMsg.equals("NoConnection error")) {
            listener.onFailure("请检查数据连接是否打开");
        } else if (errorMsg.equals("NoConnection error")) {
            listener.onFailure("网络请求身份验证错误");
        } else if (errorMsg.equals("server error") || errorMsg.equals("server error, Only throw ServerError for 5xx status codes.")) {
            listener.onFailure("服务器错误");
        } else {
            listener.onFailure("请检查网络连接是否正常");
        }
    }
}
