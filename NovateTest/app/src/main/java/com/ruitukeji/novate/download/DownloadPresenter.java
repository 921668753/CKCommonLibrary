package com.ruitukeji.novate.download;

import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.utils.MathUtil;
import com.ruitukeji.novate.R;


/**
 * Created by ruitu on 2016/9/24.
 */

public class DownloadPresenter implements DownloadContract.Presenter {
    private DownloadContract.View mView;

    public DownloadPresenter(DownloadContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void downloadApp(String updateAppUrl) {
        mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download));
//
    }
}
