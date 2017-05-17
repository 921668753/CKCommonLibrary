package com.ckcommon.utils.httputil;

import android.content.Context;

import com.kymjs.common.SystemTool;
import com.kymjs.rxvolley.client.HttpParams;

/**
 * Created by ruitu on 2016/8/25.
 */

public class HttpUtilParams {
    //volatile的作用是： 作为指令关键字，确保本条指令不会因编译器的优化而省略，且要求每次直接读值.
    //一个定义为volatile的变量是说这变量可能会被意想不到地改变，
    //这样，编译器就不会去假设这个变量的值了。
    //精确地说就是，优化器在用到这个变量时必须每次都小心地重新读取这个变量的值，而不是使用保存在寄存器里的备份。
    public static Context context1;
    private volatile static HttpUtilParams httpUtilParams = null;
    private static HttpParams httpParams;

    //构造函数 逻辑处理

    private HttpUtilParams(Context context1) {
        this.context1=context1;
        httpParams = new HttpParams();
        httpParams.putHeaders("source", "android");
        httpParams.putHeaders("version", SystemTool.getAppVersionName(context1));
    }

    /**
     *
     * @return
     * 不是很好，待改进
     */
    public static HttpUtilParams getInstance() {
        dstroyInstance();
        //第一次判断是否为空
        if (httpUtilParams == null) {
            synchronized (HttpUtilParams.class) {//锁
                //第二次判断是否为空 多线程同时走到这里的时候，需要这样优化处理
                if (httpUtilParams == null) {
                    httpUtilParams = new HttpUtilParams(context1);
                }
            }
        }

        return httpUtilParams;
    }
//    public synchronized static HttpUtilParams getInstance() {
//        httpUtilParams = null;
//        httpParams = null;
//        httpUtilParams = new HttpUtilParams();
//        return httpUtilParams;
//    }

    public HttpParams getHttpParams() {
        return httpParams;
    }

    private static synchronized void dstroyInstance() {
        if (httpUtilParams != null) {
            httpUtilParams = null;
        }
        if (httpParams != null) {
            httpParams = null;
        }
    }
}
