package com.common.cklibrary.common;

/**
 * 用于存放字符串常量的类
 * Created by ruitu ck on 2016/9/14.
 */

public class StringConstants {
    /**
     * 存放的文件名
     */
    public static String FILENAME = "share";
    /**
     * 文件缓存路径存放的文件名-----图片以及URL请求缓存路径
     */
    public static String CACHEPATH = "ATWWG/Cache";
    // 文件缓存路径存放，500M，根据自己的需求进行修改(单位bt)
    public static final int CACHE_SIZE = 500 * 1000 * 1000;
    /**
     * 图片缓存路径 存放的文件名
     */
    public static String PHOTOCACHE = "ATWWG/PhotoCache";
    /**
     * 图片保存路径存放的文件名
     */
    public static String PHOTOPATH = "ATWWG/PhonePhoto";
    /**
     * 下载文件保存路径的文件名
     */
    public static String DOWNLOADPATH = "ATWWG/Download";
    /**
     * 错误日志存放位置ERRORLOG
     */
    public static String ERRORLOG = "ATWWG/PhoneLog";

    // 图片缓存最大容量，150M，根据自己的需求进行修改
    public static final int GLIDE_CATCH_SIZE = 550 * 1000 * 1000;
    // 图片压缩，5M，根据自己的需求进行修改(单位bt)
    public static final int COMPRESSION_SIZE = 5 * 1000 * 1000;
}
