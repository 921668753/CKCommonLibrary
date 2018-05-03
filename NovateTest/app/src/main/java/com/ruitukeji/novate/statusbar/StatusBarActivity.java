package com.ruitukeji.novate.statusbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.utils.ActivityTitleUtils;
import com.githang.statusbar.StatusBarCompat;
import com.ruitukeji.novate.R;
import com.ruitukeji.novate.imagesrounded.ImagesRoundedActivity;

import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 状态栏颜色修改
 * Created by Admin on 2017/11/22.
 */

public class StatusBarActivity extends BaseActivity {

    @BindView(id = R.id.titlebar)
    private BGATitleBar titlebar;

    @BindView(id = R.id.tv_transparentStatusBar, click = true)
    private TextView tv_transparentStatusBar;

    @BindView(id = R.id.tv_whiteStatusBar, click = true)
    private TextView tv_whiteStatusBar;

    @BindView(id = R.id.tv_redStatusBar, click = true)
    private TextView tv_redStatusBar;

    @BindView(id = R.id.tv_hiddenStatusBar, click = true)
    private TextView tv_hiddenStatusBar;

    private boolean isChanged = true;

    @BindView(id = R.id.tv_imagesRounded, click = true)
    private TextView tv_imagesRounded;

    @Override
    public void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_statusbar);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        ActivityTitleUtils.initToolbar(aty, "状态栏颜色修改", true, R.id.titlebar);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_transparentStatusBar:
                StatusBarCompat.setStatusBarColor(this, Color.TRANSPARENT);
                break;
            case R.id.tv_whiteStatusBar:
                StatusBarCompat.setStatusBarColor(this, Color.WHITE);
                break;
            case R.id.tv_redStatusBar:
                StatusBarCompat.setStatusBarColor(this, Color.RED, true);
                break;
            case R.id.tv_hiddenStatusBar:
                if (isChanged) {
                    StatusBarCompat.setTranslucent(getWindow(), true);
                    StatusBarCompat.resetActionBarContainerTopMargin(getWindow());
                    isChanged = false;
                } else {
                    isChanged = true;
                    StatusBarCompat.setTranslucent(getWindow(), false);
                    StatusBarCompat.resetActionBarContainerTopMargin(getWindow());
                }
                break;
            case R.id.tv_imagesRounded:
                showActivity(aty, ImagesRoundedActivity.class);
                break;
            default:
                break;
        }
    }
}
