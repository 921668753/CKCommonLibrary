package com.ruitukeji.novate.retrofit;

import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.common.StringConstants;
import com.common.cklibrary.utils.httputil.NovateRestponse;
import com.common.cklibrary.utils.httputil.ResponseChangListener;
import com.common.cklibrary.utils.httputil.ResponseListener;
import com.kymjs.common.FileUtils;
import com.ruitukeji.novate.R;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxFileCallBack;
import com.tamic.novate.download.DownLoadCallBack;

import java.io.File;
import java.util.Map;

import static com.common.cklibrary.utils.httputil.NovateRestponse.requestHeader;


/**
 * Created by ruitu on 2016/9/17.
 */

public class RequestClient {

    /**
     * 应用配置参数
     */
    public static void getAppConfig(Map<String, Object> params, final ResponseListener<String> listener) {
        Map<String, Object> headers = requestHeader();
        headers.put("eddf", "14555");
        NovateRestponse.requestGetHttp("index.php?m=Api&c=Car&a=getCarInfo", headers, params, listener);
    }

    public static void getAppConfig1(Map<String, Object> params, final ResponseListener<String> listener) {
        Map<String, Object> headers = requestHeader();
        headers.put("eddf", "14555");
        NovateRestponse.requestGetHttp("index.php?m=Api&c=Car&a=getCarInfo", headers, params, listener);
    }

    /**
     * 下载App
     */
    @SuppressWarnings("unchecked")
    public static void downloadApp(String updateAppUrl, final ResponseChangListener<String> listener) {
        DownLoadCallBack downLoadCallBack = new DownLoadCallBack() {
            @Override
            public void onError(Throwable e) {
                NovateRestponse.doFailure(e.getCode(), e.getMessage(), listener);
            }

            @Override
            public void onProgress(String key, int progress, long fileSizeDownloaded, long totalSize) {
                super.onProgress(key, progress, fileSizeDownloaded, totalSize);
                listener.onChanged(key, progress, fileSizeDownloaded, totalSize);
            }

            @Override
            public void onSucess(String key, String path, String name, long fileSize) {
                listener.onSuccess(path);
            }
        };
        Map<String, Object> headers = requestHeader();
        NovateRestponse.requestDownload(updateAppUrl, headers, FileUtils.getSaveFolder(StringConstants.DOWNLOADPATH).getAbsolutePath() + "/", KJActivityStack.create().topActivity().getString(R.string.app_name) + ".apk", downLoadCallBack);
    }

    @SuppressWarnings("unchecked")
    public static void downloadRxApp(String updateAppUrl, final ResponseChangListener<String> listener) {
        String path = FileUtils.getSaveFolder(StringConstants.DOWNLOADPATH).getAbsolutePath() + "/";
        String fileName = KJActivityStack.create().topActivity().getString(R.string.app_name) + ".apk";
        RxFileCallBack downLoadCallBack = new RxFileCallBack(path, fileName) {
            @Override
            public void onNext(Object tag, File file) {
                listener.onSuccess(file.getPath());
            }

            @Override
            public void onProgress(Object tag, float progress, long downloaded, long total) {
                listener.onChanged((String) tag, (int) progress, downloaded, total);
            }

            @Override
            public void onError(Object tag, Throwable e) {
                NovateRestponse.doFailure(e.getCode(), e.getMessage(), listener);
            }

            @Override
            public void onCancel(Object tag, Throwable e) {
                NovateRestponse.doFailure(e.getCode(), e.getMessage(), listener);
            }

//            @Override
//            public void onError(Throwable e) {
//                NovateRestponse.doFailure(e.getCode(), e.getMessage(), listener);
//            }
//
//            @Override
//            public void onProgress(String key, int progress, long fileSizeDownloaded, long totalSize) {
//                super.onProgress(key, progress, fileSizeDownloaded, totalSize);
//                listener.onChanged(key, progress, fileSizeDownloaded, totalSize);
//            }
//
//            @Override
//            public void onSucess(String key, String path, String name, long fileSize) {
//                listener.onSuccess(path);
//            }
        };
        Map<String, Object> headers = requestHeader();
        NovateRestponse.requestRxDownload(updateAppUrl, headers, FileUtils.getSaveFolder(StringConstants.DOWNLOADPATH).getAbsolutePath() + "/", KJActivityStack.create().topActivity().getString(R.string.app_name) + ".apk", downLoadCallBack);
    }


}
