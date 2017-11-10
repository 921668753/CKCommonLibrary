package com.ruitukeji.novate.download;

import com.common.cklibrary.common.KJActivityStack;
import com.common.cklibrary.utils.MathUtil;
import com.ruitukeji.novate.R;
import com.tamic.rx.fastdown.callback.IDLCallback;
import com.tamic.rx.fastdown.client.DLClientFactory;
import com.tamic.rx.fastdown.content.DownLoadInfo;
import com.tamic.rx.fastdown.core.Download;
import com.tamic.rx.fastdown.core.Priority;

import java.util.List;

import static com.tamic.rx.fastdown.client.Type.APK;
import static com.tamic.rx.fastdown.client.Type.FRAME;

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
        IDLCallback callback = new IDLCallback() {
            @Override
            public void onStart(String key, long fileLength, long downloaded, String savePath, String filenNme) {

            }

            @Override
            public void onSuccess(String key, long fileLength, long downloaded, String savePath, String filenNme, long aSpeed, String aAppiconName) {

            }

            @Override
            public void onAppSuccess(String key, long fileLength, long downloaded, String savePath, String filenNme, long aSpeed, String aAppiconName, int downloadType, int appType) {
                mView.getSuccess(1, savePath + KJActivityStack.create().topActivity().getString(R.string.app_name) + ".apk");
            }

            @Override
            public void onFail(String key, long downloaded, String savePath, String filenNme, String aErrinfo) {
                mView.errorMsg(1, aErrinfo);
            }

            @Override
            public void onCancel(String key, long fileLength, long downloaded, String savePath, String filenNme) {

            }

            @Override
            public void onPause(String key, long fileLength, long downloaded, String savePath, String filenNme) {

            }

            @Override
            public void onDownloading(String key, long fileLength, long downloadLength, long speed, String fileName, int downloadType) {
                String size = MathUtil.keepZero(((double) downloadLength) * 100 / fileLength) + "%";
                mView.showLoadingDialog(KJActivityStack.create().topActivity().getString(R.string.download) + size);
            }

            @Override
            public void onRefresh(List<DownLoadInfo> infos) {

            }
        };
     //   String DOWNLOADPATH = "SHZY/Download";
        new Download.Builder()
                .url(updateAppUrl)
                .name("simple.apk")
                .priority(Priority.HIGH)
                .isImplicit(false)
                .type(FRAME)
         //       .savepath(DOWNLOADPATH)
                .channel(20000)
                .setCallback(callback)
          //      .client(DLClientFactory.createClient(APK, KJActivityStack.create().topActivity()))
                .build(KJActivityStack.create().topActivity())
                .start();
    }
}
