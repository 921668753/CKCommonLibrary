package com.common.cklibrary.utils.httputil;

/**
 * Created by ruitu on 2016/9/17.
 */

public interface ResponseListener<T> {

    void onSuccess(T response);

    void onFailure(int errCode, T msg);
}
