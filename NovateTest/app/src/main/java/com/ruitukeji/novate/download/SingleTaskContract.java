package com.ruitukeji.novate.download;


import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.cklibrary.common.BasePresenter;
import com.common.cklibrary.common.BaseView;
import com.othershe.dutil.download.DownloadManger;

/**
 * Created by ruitu on 2016/9/24.
 */

public interface SingleTaskContract {
    interface Presenter extends BasePresenter {
        /**
         * 下载app
         */
        void singleTaskDownloadApp(String updateAppUrl, DownloadManger downloadManger, TextView mTip, TextView mProgress, ProgressBar progressBar);

    }

    interface View extends BaseView<Presenter, String> {

    }

}


