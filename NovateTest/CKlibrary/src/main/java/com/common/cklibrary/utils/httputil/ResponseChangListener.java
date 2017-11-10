package com.common.cklibrary.utils.httputil;

/**
 * Created by Administrator on 2017/6/21.
 */

public interface ResponseChangListener<T> extends ResponseListener<T> {

    void onChanged(String key, int progress, long fileSizeDownloaded, long totalSize);

}
