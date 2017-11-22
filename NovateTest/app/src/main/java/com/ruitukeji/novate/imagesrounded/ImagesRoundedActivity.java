package com.ruitukeji.novate.imagesrounded;

import android.graphics.Color;
import android.widget.ImageView;

import com.common.cklibrary.common.BaseActivity;
import com.common.cklibrary.common.BindView;
import com.common.cklibrary.common.GlideImageLoader;
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
        GlideImageLoader.glideLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_round, 0);
        GlideImageLoader.glideLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_fillet, 1);
        GlideImageLoader.glideLeftTopLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_upperLeft);
        GlideImageLoader.glideLeftBottomLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_lowerLeft);
        GlideImageLoader.glideRightTopLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_upperRight);
        GlideImageLoader.glideRightBottomLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_lowerRight);
        GlideImageLoader.glideTopLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_on);
        GlideImageLoader.glideBottomLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_lower);
        GlideImageLoader.glideLeftLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_left);
        GlideImageLoader.glideRightLoader(this, "http://pic.58pic.com/58pic/16/62/63/97m58PICyWM_1024.jpg", img_right);
    }

}
