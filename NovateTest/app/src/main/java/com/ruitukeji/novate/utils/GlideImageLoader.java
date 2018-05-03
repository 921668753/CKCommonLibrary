package com.ruitukeji.novate.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.common.cklibrary.common.GlideApp;
import com.common.cklibrary.utils.picturerelated.GlideCatchUtil;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * 加载图片工具类
 */
@SuppressWarnings("deprecation")
public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        if (path.startsWith("http")) {
            GlideApp.with(activity)
                    //      .asBitmap()//配置上下文
                    .load(path)//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                    .error(com.common.cklibrary.R.mipmap.placeholderfigure)           //设置错误图片
//                    //  .placeholder(R.mipmap.load)     //设置占位图片
//                    .fallback(com.common.cklibrary.R.mipmap.placeholderfigure)//当url为空时，显示图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    //     .centerInside()
                    //  .transition(withCrossFade().crossFade())//应用在淡入淡出
                    //  .skipMemoryCache(true)//设置跳过内存缓存
                    .into(imageView);
        } else {
            Uri contentUri = null;
            //调试，暂注释   ---qi
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                contentUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", new File(path));
//            } else {
            contentUri = Uri.fromFile(new File(path));
//            }
            if (contentUri == null) {
                // ViewInject.toast(KJActivityStack.create().topActivity().getString(R.string.imagePathError));
                return;
            }
            GlideApp.with(activity)
                    //    .asBitmap()//配置上下文
                    .load(contentUri)//设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                    .error(com.common.cklibrary.R.mipmap.placeholderfigure)           //设置错误图片
//                    //  .placeholder(R.mipmap.load)     //设置占位图片
//                    .fallback(com.common.cklibrary.R.mipmap.placeholderfigure)//当url为空时，显示图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    //        .centerInside()
                    //  .transition(withCrossFade().crossFade())//应用在淡入淡出
                    //  .skipMemoryCache(true)//设置跳过内存缓存
                    .into(imageView);
        }
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideApp.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);
    }


    @Override
    public void clearMemoryCache() {
        // 必须在UI线程中调用
        GlideCatchUtil.getInstance().clearCacheMemory();
        // 必须在后台线程中调用，建议同时clearMemory()
        GlideCatchUtil.getInstance().clearCacheDiskSelf();
    }

}
