package com.common.cklibrary.common;

/**
 * Created by ruitu on 2017/6/24.
 */

public interface BaseView<T, E> extends LoadingDialogView {

    void setPresenter(T presenter);

    /**
     * http请求正确
     *
     * @param success 成功的信息
     * @param flag    用于区别请求
     */
    void getSuccess(int flag, E success);

    /**
     * http请求错误
     */
    void errorMsg(int errorCode, E msg);

}