package com.common.cklibrary.utils.logoutput;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.common.cklibrary.common.KJActivityStack;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import cn.jesse.nativelogger.formatter.TagFormatter;

/**
 * 异常格式化工具类
 * Created by Admin on 2017/11/21.
 */
@SuppressWarnings("deprecation")
public class SimpleNewFormatter extends Formatter {

    public static final String TAG = SimpleNewFormatter.class.getCanonicalName();

    private static final String LINE_SEPARATOR = "\n";

    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    public SimpleNewFormatter() {
        //unused
    }

    /**
     * Converts a object into a human readable string
     * representation.
     */
    @Override
    public String format(LogRecord r) {
        collectDeviceInfo(KJActivityStack.create().topActivity());
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append("[" + key + ", " + value + "]\n");
        }
        sb.append(MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss} ",
                new Object[]{new Date(r.getMillis())}));
        sb.append(r.getLoggerName()).append(": ");
        sb.append(r.getLevel().getName()).append(LINE_SEPARATOR);
        sb.append(formatMessage(r)).append(LINE_SEPARATOR);
        if (r.getThrown() != null) {
            sb.append("Throwable occurred: ");
            sb.append(TagFormatter.format(r.getThrown()));
        }
        sb.append(LINE_SEPARATOR);
        sb.append(LINE_SEPARATOR);
        sb.append(LINE_SEPARATOR);
        return sb.toString();
    }


    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }

        TelephonyManager mTelephonyMgr = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String imei = mTelephonyMgr.getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            imei = "unknownimei";
        }
        infos.put("imei", imei);
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

}
