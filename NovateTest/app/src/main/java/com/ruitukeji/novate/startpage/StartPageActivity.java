package com.ruitukeji.novate.startpage;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.common.cklibrary.common.BaseActivity;
import com.ruitukeji.novate.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户端
 * 启动页暂定为集成beasactivity
 * 若添加极光推送等需更换极光推送activity   InstrumentedActivity
 * Created by ruitu ck on 2016/9/14.
 */

public class StartPageActivity extends BaseActivity implements StartPageContract.View {

    private static final int REQUEST_CODE_PERMISSION_SINGLE = 101;

    private static final int REQUEST_CODE_SETTING = 300;
    private ArrayList permissions = null;
    String[] perms = null;

    @Override
    public void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wxpay_call_back);
    }

    /**
     * 设置定位
     */
    @Override
    public void initData() {
        super.initData();
        mPresenter = new StartPagePresenter(this);
        permissions = new ArrayList<>();
        perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.CHANGE_WIFI_STATE};
        permissions.addAll(Arrays.asList(perms));
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
        image.setImageResource(R.mipmap.ic_launcher);
        Animation anim = AnimationUtils.loadAnimation(aty, R.anim.splash_start);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getPermission();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        image.setAnimation(anim);
        setContentView(image);
    }

    @Override
    public void setPresenter(StartPageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getSuccess(int flag, String success) {

    }

    @Override
    public void errorMsg(int errorCode, String msg) {

    }

    /**
     * 申请权限  拍照和读写权限
     */
    public void getPermission() {
        // 申请多个权限。
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_PERMISSION_SINGLE)
                .permission(perms)
                .callback(permissionListener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(StartPageActivity.this, rationale).show();
                    }
                })
                .start();
    }


    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION_SINGLE: {
                    // TODO do something.
                    Toast.makeText(StartPageActivity.this, R.string.successfully, Toast.LENGTH_SHORT).show();
                    ((StartPageContract.Presenter) mPresenter).jumpTo(StartPageActivity.this);
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION_SINGLE: {
                    // TODO The strategy after failure.
                    Toast.makeText(StartPageActivity.this, R.string.failure, Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(StartPageActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(StartPageActivity.this, REQUEST_CODE_SETTING).show();

                // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingService.execute();
//            你的dialog点击了取消调用：
//            settingService.cancel();
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING:
                if (AndPermission.hasPermission(this, permissions)) {
                    // TODO ...
                    Toast.makeText(this, R.string.message_setting_back, Toast.LENGTH_LONG).show();
                    ((StartPageContract.Presenter) mPresenter).jumpTo(StartPageActivity.this);
                } else {
                    AndPermission.defaultSettingDialog(StartPageActivity.this, REQUEST_CODE_SETTING).show();
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
