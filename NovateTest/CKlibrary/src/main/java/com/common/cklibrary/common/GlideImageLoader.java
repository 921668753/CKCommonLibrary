package com.common.cklibrary.common;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.common.cklibrary.R;
import com.common.cklibrary.utils.GlideCatchUtil;
import com.common.cklibrary.utils.GlideCircleTransform;
import com.common.cklibrary.utils.GlideRoundTransform;
import com.lzy.imagepicker.loader.ImageLoader;

/**
 * 加载图片工具类
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
//Uri.fromFile(new File(path))
        GlideApp.with(activity)                      //配置上下文
                .load(path)//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                .error(R.mipmap.default_image)           //设置错误图片
//                .placeholder(R.mipmap.default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                //  .transition(withCrossFade().crossFade())//应用在淡入淡出
                //  .skipMemoryCache(true)//设置跳过内存缓存
                .into(imageView);


    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {

    }

    @Override
    public void clearMemoryCache() {
        // 必须在UI线程中调用
        GlideCatchUtil.getInstance().clearCacheMemory();
        // 必须在后台线程中调用，建议同时clearMemory()
        GlideCatchUtil.getInstance().clearCacheDiskSelf();
    }


    /**
     * @param context
     * @param url
     * @param imageView
     * @param tag       ==0为圆形  ==1为圆角
     */
    public static void glideLoader(Context context, Object url, ImageView imageView, int tag) {

        if (0 == tag) {
            GlideApp.with(context)
                    .load(url)
                    //  .skipMemoryCache(true)//设置跳过内存缓存
                    .placeholder(R.drawable.headimg)
                    .error(R.drawable.headimg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideCircleTransform(context))
                    .dontAnimate()//没有任何淡入淡出效果
                    //    .transition(withCrossFade().crossFade())//应用在淡入淡出
                    .into(imageView);
        } else if (1 == tag) {
            GlideApp.with(context)
                    .load(url)
//                    .placeholder(R.mipmap.default_image)
//                    .error(R.mipmap.default_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new GlideRoundTransform(context, 10))
                    //   .skipMemoryCache(true)//设置跳过内存缓存
                    .dontAnimate()//没有任何淡入淡出效果
                    //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                    .into(imageView);
        }
    }

    /**
     * 普通加载
     */
    public static void glideOrdinaryLoader(Context context, Object url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                //  .skipMemoryCache(true)//设置跳过内存缓存
//                .placeholder(R.mipmap.default_image)
//                .error(R.mipmap.default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .dontAnimate()
//                .transition(withCrossFade())//应用在淡入淡出
                .into(imageView);
    }


}
