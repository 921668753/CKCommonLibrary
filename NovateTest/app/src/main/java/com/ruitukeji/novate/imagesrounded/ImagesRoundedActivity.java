package com.ruitukeji.novate.imagesrounded;

import android.graphics.Color;
import android.widget.ImageView;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.utils.picturerelated.GlideImageLoader;
import com.common.cklibrary.utils.ActivityTitleUtils;
import com.githang.statusbar.StatusBarCompat;
import com.ruitukeji.novate.R;

import cn.bingoogolapple.titlebar.BGATitleBar;

/**
 * 图片圆角
 * Created by Admin on 2017/11/22.
 */

public class ImagesRoundedActivity extends BaseActivity {

    @BindView(id = R.id.titlebar)
    private BGATitleBar titlebar;

    @BindView(id = R.id.img_round)
    private ImageView img_round;

    @BindView(id = R.id.img_fillet)
    private ImageView img_fillet;

    @BindView(id = R.id.img_upperLeft)
    private ImageView img_upperLeft;

    @BindView(id = R.id.img_lowerLeft)
    private ImageView img_lowerLeft;

    @BindView(id = R.id.img_upperRight)
    private ImageView img_upperRight;

    @BindView(id = R.id.img_lowerRight)
    private ImageView img_lowerRight;

    @BindView(id = R.id.img_on)
    private ImageView img_on;

    @BindView(id = R.id.img_lower)
    private ImageView img_lower;

    @BindView(id = R.id.img_left)
    private ImageView img_left;

    @BindView(id = R.id.img_right)
    private ImageView img_right;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_imagesrounded);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        StatusBarCompat.setStatusBarColor(this, Color.RED);
        ActivityTitleUtils.initToolbar(aty, "图片圆角", true, R.id.titlebar);
        initView();
    }

    /**
     * 加载图片
     */
    public void initView() {
        GlideImageLoader.glideRoundLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_round, R.mipmap.headimg);
        GlideImageLoader.glideRoundRectangleLoader(this, "http://pic29.photophoto.cn/20131204/0034034499213463_b.jpg", 50, img_fillet, R.mipmap.ic_launcher);
        GlideImageLoader.glideLeftTopLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", 50, img_upperLeft, R.mipmap.ic_launcher);
        GlideImageLoader.glideLeftBottomLoader(this, "http://pic7.nipic.com/20100614/4942616_212756029322_2.jpg", 50, img_lowerLeft, R.mipmap.ic_launcher);
        GlideImageLoader.glideRightTopLoader(this, "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=157a7116a5c3793169658e6a83addd30/0b55b319ebc4b7452e230406c5fc1e178a82156e.jpg", 50, img_upperRight, R.mipmap.ic_launcher);
        GlideImageLoader.glideRightBottomLoader(this, "http://pic32.photophoto.cn/20140711/0011024086081224_b.jpg", 50, img_lowerRight, R.mipmap.ic_launcher);
        GlideImageLoader.glideTopLoader(this, "http://img.taopic.com/uploads/allimg/130331/240460-13033106243430.jpg", 50, img_on, R.mipmap.ic_launcher);
        GlideImageLoader.glideBottomLoader(this, "http://img1.3lian.com/2015/a1/95/d/105.jpg", 50, img_lower, R.mipmap.ic_launcher);
        GlideImageLoader.glideLeftLoader(this, "http://pic20.photophoto.cn/20110827/0013026447172846_b.jpg", 50, img_left, R.mipmap.ic_launcher);
        GlideImageLoader.glideRightLoader(this, "http://img1.3lian.com/2015/a1/84/d/95.jpg", 50, img_right, R.mipmap.ic_launcher);
    }
}
