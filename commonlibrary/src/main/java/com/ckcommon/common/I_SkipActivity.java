package com.ckcommon.common;

/**
 * Created by Administrator on 2017/3/7.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 规范Activity跳转的接口协议
 *
 * @author kymjs (https://github.com/kymjs)
 */
public interface I_SkipActivity {
    /**
     *
     * @param aty finish() method
     * @param cls
     *   skip to @param(cls)，and call @param(aty's)
     */

    public void skipActivity(Activity aty, Class<?> cls);

    /**
     *
     * @param aty
     * finish() method
     * @param it
     * 传递参数
     */
    public void skipActivity(Activity aty, Intent it);

    /**
     *
     * @param aty
     * 传递参数
     * @param cls
     * 传递参数
     * @param extras
     * 传递参数
     */
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras);

    /**
     *
     * @param aty
     * 传递参数
     * @param cls
     * 传递参数
     */
    public void showActivity(Activity aty, Class<?> cls);

    /**
     *
     * @param aty
     * 传递参数
     * @param it
     * 传递参数
     */
    public void showActivity(Activity aty, Intent it);

    /**
     *
     * @param aty
     * 传递参数
     * @param cls
     * 传递参数
     * @param extras
     * 传递参数
     */
    public void showActivity(Activity aty, Class<?> cls, Bundle extras);
}
