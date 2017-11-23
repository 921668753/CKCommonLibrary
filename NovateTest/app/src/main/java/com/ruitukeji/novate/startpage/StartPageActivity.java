package com.ruitukeji.novate.startpage;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.StringConstants;
import com.kymjs.common.PreferenceHelper;
import com.ruitukeji.novate.MainActivity;

/**
 * 用户端
 * 启动页暂定为集成beasactivity
 * 若添加极光推送等需更换极光推送activity   InstrumentedActivity
 * Created by ruitu ck on 2016/9/14.
 */

public class StartPageActivity extends BaseActivity implements StartPageContract.View {
    private StartPageContract.Presenter mPresenter;

    /**
     * 高德定位
     */
    @Override
    public void setRootView() {
     //   setContentView(R.layout.activity_startpage);
    }

    /**
     * 设置定位
     */
    @Override
    public void initData() {
        super.initData();
        mPresenter = new StartPagePresenter(this);
//        mLocationClient = new LocationClient(aty.getApplicationContext());
//        myListener = new MyLocationListener();
//        //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);
//        //注册监听函数
//        ((StartPageContract.Presenter) mPresenter).initLocation(aty, mLocationClient);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initView();
    }

    public void initView() {
        ImageView image = new ImageView(aty);
//        image.setImageResource(R.mipmap.startpage);
//        Animation anim = AnimationUtils.loadAnimation(aty, R.anim.splash_start);
//        anim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//              //  readAndWriteTask();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
    //    image.setAnimation(anim);
        setContentView(image);
    }

    private void jumpTo() {
//        startService(new Intent(aty, CommonService.class));
        boolean isFirst = PreferenceHelper.readBoolean(this, StringConstants.FILENAME, "firstOpen", true);
        Intent jumpIntent = new Intent();
//        if (isFirst) {
//            PreferenceHelper.write(this, StringConstants.FILENAME, "firstOpen", false);
//            jumpIntent.setClass(this, GuideViewActivity.class);
//        } else {
        jumpIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        jumpIntent.setAction("android.intent.action.MAIN");
        jumpIntent.addCategory("android.intent.category.LAUNCHER");
        jumpIntent.setClass(this, MainActivity.class);
        //    }
        skipActivity(aty, jumpIntent);
        overridePendingTransition(0, 0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mLocationClient.unRegisterLocationListener(myListener); //注销掉监听
//        mLocationClient.stop(); //停止定位服务
//        myListener = null;
    }

    @Override
    public void setPresenter(StartPageContract.Presenter presenter) {

    }

    @Override
    public void getSuccess(int flag, String success) {

    }

    @Override
    public void errorMsg(int errorCode, String msg) {

    }
}
