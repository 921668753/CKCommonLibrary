package com.ruitukeji.novate.startpage;

import android.content.Intent;

import com.common.cklibrary.common.StringConstants;
import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.novate.main.MainActivity;

/**
 * Created by Administrator on 2016/11/29.
 */

public class StartPagePresenter implements StartPageContract.Presenter {
    private StartPageContract.View mView;

    public StartPagePresenter(StartPageContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getAppConfig() {


    }

    @Override
    public void jumpTo(StartPageActivity aty) {
        boolean isFirst = PreferenceHelper.readBoolean(aty, StringConstants.FILENAME, "firstOpen", true);
        Intent jumpIntent = new Intent();
        if (isFirst) {
            PreferenceHelper.write(aty, StringConstants.FILENAME, "firstOpen", false);
            jumpIntent.setClass(aty, GuideViewActivity.class);
        } else {
            jumpIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            jumpIntent.setAction("android.intent.action.MAIN");
            jumpIntent.addCategory("android.intent.category.LAUNCHER");
            jumpIntent.setClass(aty, MainActivity.class);
        }
        aty.skipActivity(aty, jumpIntent);
        aty.overridePendingTransition(0, 0);
    }
}
