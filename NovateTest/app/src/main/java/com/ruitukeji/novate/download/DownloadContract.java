package com.ruitukeji.novate.download;


import com.common.cklibrary.common.BasePresenter;
import com.common.cklibrary.common.BaseView;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface DownloadContract {
    interface Presenter extends BasePresenter {
        /**
         * 下载app
         */
        void downloadApp(String updateAppUrl);

    }

    interface View extends BaseView<Presenter, String> {

    }

}


