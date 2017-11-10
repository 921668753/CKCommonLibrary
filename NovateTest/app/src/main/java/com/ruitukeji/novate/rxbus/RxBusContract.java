package com.ruitukeji.novate.rxbus;


import com.common.cklibrary.common.BasePresenter;
import com.common.cklibrary.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface RxBusContract {
    interface Presenter extends BasePresenter {
        /**
         * 应用配置参数
         */
        void getAppConfig();

        /**
         * 下载app
         */
        void downloadApp(String updateAppUrl);

    }

    interface View extends BaseView<Presenter, String> {

    }

}


