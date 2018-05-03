package com.common.cklibrary.utils.picturerelated;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.common.cklibrary.common.GlideApp;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * 加载图片工具类
 */
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


    /**
     * 普通加载
     */
    public static void glideOrdinaryLoader(Context context, Object url, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                //  .skipMemoryCache(true)//设置跳过内存缓存
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .dontAnimate()
//                .transition(withCrossFade())//应用在淡入淡出
                .into(imageView);
    }


    /**
     * @param context
     * @param url
     * @param imageView 圆形
     */
    public static void glideRoundLoader(Context context, Object url, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                //  .skipMemoryCache(true)//设置跳过内存缓存
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(context))
                //   .dontAnimate()//没有任何淡入淡出效果
                //    .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }


    /**
     * @param context
     * @param url
     * @param imageView 圆角矩形
     */
    public static void glideRoundRectangleLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.ALL))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }


    /**
     * 左上
     */
    public static void glideLeftTopLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.LEFT_TOP))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 左下
     */
    public static void glideLeftBottomLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.LEFT_BOTTOM))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }


    /**
     * 右上
     */
    public static void glideRightTopLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.RIGHT_TOP))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 右下
     */
    public static void glideRightBottomLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.RIGHT_BOTTOM))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 上侧
     */
    public static void glideTopLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.TOP))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 下侧
     */
    public static void glideBottomLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.BOTTOM))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 左侧
     */
    public static void glideLeftLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.LEFT))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }

    /**
     * 右侧
     */
    public static void glideRightLoader(Context context, Object url, int radius, ImageView imageView, int default_image) {
        GlideApp.with(context)
                .load(url)
                .placeholder(default_image)
                .error(default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundCornersTransformation(context, radius, RoundCornersTransformation.CornerType.RIGHT))
                //   .skipMemoryCache(true)//设置跳过内存缓存
                .dontAnimate()//没有任何淡入淡出效果
                //   .transition(withCrossFade().crossFade())//应用在淡入淡出
                .into(imageView);
    }
}
