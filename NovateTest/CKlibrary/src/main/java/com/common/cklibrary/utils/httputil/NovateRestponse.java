package com.common.cklibrary.utils.httputil;

import com.common.cklibrary.R;
import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.common.StringConstants;
import com.common.cklibrary.entity.BaseResult;
import com.common.cklibrary.utils.JsonUtil;
import com.kymjs.common.FileUtils;
import com.kymjs.common.Log;
import com.kymjs.common.StringUtils;
import com.kymjs.common.SystemTool;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.RxApiManager;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.ResponseCallback;
import com.tamic.novate.download.DownLoadCallBack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;

/**
 * 网络请求
 * Created by Administrator on 2017/6/20.
 */

@SuppressWarnings("unchecked")
public class NovateRestponse {

    public static int TOLINGIN = -10001;
    public static int SUCCESS = 2000;
    public static String baseUrl = "http://shz.api.user.ruitukeji.cn:8502/";
    private static Map<String, Object> headers = null;
    private static Map<String, Object> parameters = null;
    private static Novate.Builder novateBuilder = null;
    private static BaseSubscriber myBaseSubscriber = null;

    public static Novate.Builder requestNovateBuilder(boolean isCache) {

        if (novateBuilder == null) {
            novateBuilder = new Novate.Builder(KJActivityStack.create().topActivity())
                    //  .addHeader(headers) //添加公共请求头
                    //   .addParameters(parameters)//公共参数
                    .connectTimeout(120)  //连接时间 可以忽略
                    .writeTimeout(200)
                    .addCookie(false)  //是否同步cooike 默认不同步
                    .addCache(isCache)  //是否缓存 默认缓存
                    //    .addCache(new Cache(FileUtils.getSaveFolder(StringConstants.CACHEPATH), StringConstants.CACHE_SIZE)) //自定义缓存
                    .baseUrl(baseUrl) //base URL
                    .addLog(true); //是否开启log
            //   .cookieManager(new NovateCookieManager()) // 自定义cooike，可以忽略
            //     .addInterceptor() // 自定义Interceptor
            //      .addNetworkInterceptor() // 自定义NetworkInterceptor
            //     .proxy(proxy) //代理
            //       .client(client)  //clent 默认不需要
        }
        if (isCache) {
            novateBuilder = novateBuilder.addCache(new Cache(FileUtils.getSaveFolder(StringConstants.CACHEPATH), StringConstants.CACHE_SIZE), 1);  //自定义缓存
        }
        return novateBuilder;
    }

    /**
     * 请求返回
     *
     * @param responseListener
     * @return
     */
    public static BaseSubscriber requestMyBaseSubscriber(final ResponseListener responseListener) {
        myBaseSubscriber = new MyBaseSubscriber<ResponseBody>() {
            @Override
            public void onError(Throwable e) {
                //       novateBuilder = null;
                //      myBaseSubscriber = null;
                Log.d("tag", "onError");
                doFailure(e.getCode(), e.getMessage(), responseListener);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                //       novateBuilder = null;
                //  myBaseSubscriber = null;
                Log.d("tag", "onNext");
                try {
                    String jstr = new String(responseBody.bytes());
                    if (jstr.trim().isEmpty()) {
                        responseListener.onFailure(-1, KJActivityStack.create().topActivity().getString(R.string.responseDataEmpty));
                        return;
                    }
                    doSuccess(jstr, responseListener);
                } catch (IOException e) {
                    e.printStackTrace();
                    responseListener.onFailure(-1, KJActivityStack.create().topActivity().getString(R.string.responseDataEmpty));
                }
            }
        };
        return myBaseSubscriber;
    }

    /**
     * 添加请求头
     */
    public static Map<String, Object> requestHeader() {
        if (headers == null) {
            headers = new HashMap<String, Object>();
        }
        headers.clear();
        headers.put("language", Locale.getDefault().getLanguage());//语言简称
        headers.put("country", Locale.getDefault().getCountry());//国家简称
        headers.put("source", "android");
        headers.put("appVersion", SystemTool.getAppVersionName(KJActivityStack.create().topActivity()));//app版本
        return headers;
    }

    /**
     * 添加请求参数
     */
    public static Map<String, Object> requestParameters() {
        if (parameters == null) {
            parameters = new HashMap<String, Object>();
        }
        parameters.clear();
        return parameters;
    }

    /**
     * get请求
     *
     * @param url
     * @param responseListener
     */
    public static void requestGetHttp(String url, Map<String, Object> headers, Map<String, Object> params, ResponseListener responseListener) {
        Subscription subscription = (Subscription) requestNovateBuilder(false).addHeader(headers).build().get(url, params, requestMyBaseSubscriber(responseListener));
        RxApiManager.get().add(KJActivityStack.create().topActivity().getClass().getName(), subscription);
    }

    /**
     * Post请求
     *
     * @param url
     * @param responseListener
     */
    public static void requestPostHttp(String url, Map<String, Object> headers, Map<String, Object> params, ResponseListener responseListener) {
        Subscription subscription = (Subscription) requestNovateBuilder(false).addHeader(headers).build().post(url, params, requestMyBaseSubscriber(responseListener));
        RxApiManager.get().add(KJActivityStack.create().topActivity().getClass().getName(), subscription);
    }

    public static void requestJsonHttp(String url, Map<String, Object> headers, Map<String, Object> params, ResponseListener responseListener) {
        Subscription subscription = (Subscription) requestNovateBuilder(false).addHeader(headers).build().json(url, JsonUtil.getInstance().obj2JsonString(params), requestMyBaseSubscriber(responseListener));
        RxApiManager.get().add(KJActivityStack.create().topActivity().getClass().getName(), subscription);
    }


    /**
     * Put请求
     *
     * @param url
     * @param responseListener
     */
    public static void requestPutHttp(String url, Map<String, Object> headers, Map<String, Object> params, ResponseListener responseListener) {
        Subscription subscription = (Subscription) requestNovateBuilder(false).addHeader(headers).build().put(url, params, requestMyBaseSubscriber(responseListener));
        RxApiManager.get().add(KJActivityStack.create().topActivity().getClass().getName(), subscription);
    }

    /**
     * Delete请求
     *
     * @param url
     * @param responseListener
     */
    public static void requestDeleteHttp(String url, Map<String, Object> headers, Map<String, Object> params, ResponseListener responseListener) {
        Subscription subscription = (Subscription) requestNovateBuilder(false).addHeader(headers).build().delete(url, params, requestMyBaseSubscriber(responseListener));
        RxApiManager.get().add(KJActivityStack.create().topActivity().getClass().getName(), subscription);
    }

    /**
     * 上传图片请求
     *
     * @param url
     * @param responseListener
     */
    public static void requestUploadImage(String url, Map<String, Object> headers, Map<String, Object> params, File file, ResponseListener responseListener) {
        Subscription subscription = (Subscription) requestNovateBuilder(false).addHeader(headers).addParameters(params).build().uploadImage(url, file, requestMyBaseSubscriber(responseListener));
        RxApiManager.get().add(KJActivityStack.create().topActivity().getClass().getName(), subscription);
    }

    /**
     * 上传多张图片请求
     *
     * @param url
     * @param responseListener
     */
    public static void requestUploadFiles(String url, Map<String, Object> headers, Map<String, Object> params, Map<String, RequestBody> files, ResponseListener responseListener) {
//        File file = new File(path);
//        // 创建 RequestBody，用于封装 请求RequestBody
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        Map<String,  RequestBody> files = new HashMap<>();
//        files.put("image", requestFile);
        Subscription subscription = (Subscription) requestNovateBuilder(false).addHeader(headers).addParameters(params).build().uploadFlies(url, files, requestMyBaseSubscriber(responseListener));
        RxApiManager.get().add(KJActivityStack.create().topActivity().getClass().getName(), subscription);
    }

    /**
     * 下载请求
     *
     * @param url
     */
    public static void requestDownload(String url, Map<String, Object> headers, String savePath, String name, DownLoadCallBack downLoadCallBack) {
        Subscription subscription = (Subscription) requestNovateBuilder(false).addHeader(headers).build().download(null, url, savePath, name, downLoadCallBack);
        RxApiManager.get().add(KJActivityStack.create().topActivity().getClass().getName(), subscription);
    }

    public static void requestRxDownload(String url, Map<String, Object> headers, String savePath, String name, ResponseCallback downLoadCallBack) {
        Subscription subscription = (Subscription) requestNovateBuilder(false).addHeader(headers).build().rxDownload(KJActivityStack.create().topActivity().getClass().getName(), url, downLoadCallBack);
        RxApiManager.get().add(KJActivityStack.create().topActivity().getClass().getName(), subscription);
    }

    /**
     * 网络请求成功
     *
     * @param s
     * @param listener
     */
    @SuppressWarnings("unchecked")
    public static boolean doSuccess(String s, ResponseListener listener) {
        BaseResult baseResult = (BaseResult) JsonUtil.getInstance().json2Obj(s, BaseResult.class);

        if (baseResult == null) {
            listener.onFailure(-1, KJActivityStack.create().bottomActivity().getString(R.string.jsonError));
            return false;
        }

        if (baseResult.getStatus() != SUCCESS) {
            if (baseResult.getStatus() == 4011 || baseResult.getStatus() == 4012 || baseResult.getStatus() == 4013 || baseResult.getStatus() == 4015) {
                listener.onFailure(baseResult.getStatus(), TOLINGIN + "");
                return false;
            }
            listener.onFailure(baseResult.getStatus(), baseResult.getMsg());
            return false;
        }
//        if (baseResult.getResult() == null || JsonUtil.obj2JsonString(baseResult.getResult()) == null || JsonUtil.obj2JsonString(baseResult.getResult()).length() <= 2) {
//            listener.onFailure(KJActivityStack.create().topActivity().getString(R.string.serverReturnsDataNull));
//            return false;
//        }
        listener.onSuccess(s);
        return true;
    }

    /**
     * 接口返回失败信息处理
     *
     * @param errorMsg
     * @param listener
     */
    public static void doFailure(int errCode, String errorMsg, ResponseListener listener) {
        Log.d("tag", errCode + "错误原因：" + errorMsg);
        Log.d("tag", KJActivityStack.create().topActivity().getClass().getName());
        if (errCode == -1) {
            if (StringUtils.isEmpty(errorMsg)) {
                listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.clientError));
            } else if (errorMsg.contains("java.lang.IllegalArgumentException")) {
                listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.illegalArgumentException));
            } else if (errorMsg.contains("NetWork err")) {
                listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.checkNetwork));
            } else {
                listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.clientError));
            }
        } else if (errCode == TOLINGIN) {
            listener.onFailure(errCode, TOLINGIN + "");
        } else if (errCode == 1006) {
            listener.onFailure(errCode, errorMsg);
        } else if (errCode == 400) {
            listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.grammarError));
        } else if (errCode == 401 || errCode == 407) {
            listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.authenticationError));
        } else if (errCode == 403) {
            listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.serverRejectsRequest));
        } else if (errCode == 404) {
            listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.urlError));
        } else if (errCode > 404 && errCode < 500) {
            listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.requestError));
        } else if (errCode >= 500) {
            listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.serverError));
        } else {
            listener.onFailure(errCode, KJActivityStack.create().topActivity().getString(R.string.otherError));
        }
    }
}
