package com.ruitukeji.novate.permission;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.ruitukeji.novate.R;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限申请
 * Created by Admin on 2017/11/20.
 */

public class PermissionActivity extends BaseActivity {


    private static final int REQUEST_CODE_PERMISSION_SINGLE = 100;
    private static final int REQUEST_CODE_PERMISSION_MULTI = 101;

    private static final int REQUEST_CODE_SETTING = 300;

    @BindView(id = R.id.tv_camera, click = true)
    private TextView tv_camera;

    @BindView(id = R.id.tv_contactSms, click = true)
    private TextView tv_contactSms;

    List<String> permissions = null;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_permission);
    }

    @Override
    public void initData() {
        super.initData();
        permissions = new ArrayList<>();
        permissions.addAll(Arrays.asList(Permission.CONTACTS));
        permissions.addAll(Arrays.asList(Permission.SMS));
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.tv_camera:
                // 申请单个权限。
                AndPermission.with(this)
                        .requestCode(REQUEST_CODE_PERMISSION_SINGLE)
                        .permission(Permission.CAMERA)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(PermissionActivity.this, rationale).show();
                            }
                        })
                        .start();
                break;
            case R.id.tv_contactSms:
                // 申请多个权限。
                AndPermission.with(this)
                        .requestCode(REQUEST_CODE_PERMISSION_MULTI)
                        .permission(Permission.CONTACTS, Permission.SMS)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(PermissionActivity.this, rationale).show();
                            }
                        })
                        .start();
                break;
            default:
                break;
        }
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
                    Toast.makeText(PermissionActivity.this, R.string.successfully, Toast.LENGTH_SHORT).show();
                    break;
                }
                case REQUEST_CODE_PERMISSION_MULTI: {
                    // TODO do something.
                    Toast.makeText(PermissionActivity.this, R.string.successfully, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION_SINGLE: {
                    // TODO The strategy after failure.
                    Toast.makeText(PermissionActivity.this, R.string.failure, Toast.LENGTH_SHORT).show();
                    break;
                }
                case REQUEST_CODE_PERMISSION_MULTI: {
                    // TODO The strategy after failure.
                    Toast.makeText(PermissionActivity.this, R.string.failure, Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(PermissionActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(PermissionActivity.this, REQUEST_CODE_SETTING).show();

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
                if (AndPermission.hasPermission(this, Permission.CAMERA)) {
                    // TODO ...
                    Toast.makeText(this, R.string.message_setting_back, Toast.LENGTH_LONG).show();
                } else if (AndPermission.hasPermission(this, permissions)) {
                    Toast.makeText(this, R.string.message_setting_back, Toast.LENGTH_LONG).show();
                } else {
                    //  AndPermission.defaultSettingDialog(PermissionActivity.this, REQUEST_CODE_SETTING).show();
                }
                break;
        }
    }


}
