package com.ckcommon.utils.myview;

/**
 * Created by ruitu on 2016/10/11.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 自定义Webview页面
 * 我的博客：http://blog.csdn.net/sinat_25689603
 * 作者：yedongyang
 * created by ydy on 2016/7/15 9:32
 */
public class WebViewLayout extends LinearLayout {

    private LayoutInflater inflater;
    private View titleView;//头部
    private ProgressBar progressbar;//进度条
    private WebView webView;//网页

    private ImageView titleLeft;//返回
    private ImageView titleBefore;//返回前一个网页
    private TextView titleText;//标题
    private ImageView titleNext;//进入下一个网页
    private ImageView titleRight;//刷新

    private boolean isUpdateTitle;//是否根据网页改变title
    private boolean isShowIconBack;//是否显示上一页下一页图标
    private boolean isJavaScriptEnabled;//是否允许JavaScript
    private int titleHeight;//头部高度

    private WebViewCallBack callBack;//回调

    public WebViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
     //   init(context, attrs);
    }

    public WebViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
      // init(context, attrs);
    }

//    @SuppressLint("SetJavaScriptEnabled")
//    private void init(Context context, AttributeSet attrs) {
//        setOrientation(LinearLayout.VERTICAL);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WebViewLayout);
//        isUpdateTitle = typedArray.getBoolean(R.styleable.WebViewLayout_isUpdateTitle, false);
//        isShowIconBack = typedArray.getBoolean(R.styleable.WebViewLayout_isShowIconBack, false);
//        isJavaScriptEnabled = typedArray.getBoolean(R.styleable.WebViewLayout_isJavaScriptEnabled, false);
//        typedArray.recycle();
//
//        //添加头部
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        titleView = inflater.inflate(R.layout.webview_title_bar, null, false);
//        titleView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, DensityUtils.dip2px(MyApplication.getContext(), 48)));
//        titleLeft = (ImageView) titleView.findViewById(R.id.title_left);
//        titleBefore = (ImageView) titleView.findViewById(R.id.title_before);
//        titleText = (TextView) titleView.findViewById(R.id.title_text);
//        titleNext = (ImageView) titleView.findViewById(R.id.title_next);
//        titleRight = (ImageView) titleView.findViewById(R.id.title_right);
//        progressbar = (ProgressBar) titleView.findViewById(R.id.progress);
//        addView(titleView);
//
//        //添加webview
//        webView = new WebView(context);
//        webView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        webView.getSettings().setJavaScriptEnabled(isJavaScriptEnabled);
//        webView.setWebViewClient(new WebViewClient());
//        webView.setWebChromeClient(new WebChromeClient());
//        addView(webView);
//
//        setTitleView();//设置标题栏
//    }

    /**
     * 设置标题栏
     */
    private void setTitleView() {
        titleLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.backOnclick();
                }
            }
        });
        titleBefore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();
            }
        });
        titleNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goForward();
            }
        });
        titleRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            if (isUpdateTitle)
                titleText.setText(view.getTitle());

            boolean back = view.canGoBack();
            boolean forward = view.canGoForward();
            if (back || forward) {
                titleBefore.setVisibility(isShowIconBack && back ? View.VISIBLE : View.GONE);
                titleNext.setVisibility(isShowIconBack && forward ? View.VISIBLE : View.GONE);
            } else {
                titleBefore.setVisibility(View.GONE);
                titleNext.setVisibility(View.GONE);
            }
        }
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    /**
     * 设置标题栏文字，只有在isUpdateTitle为false时有用
     */
    public void setTitleText(String text) {
        if (!isUpdateTitle) {
            titleText.setText(text);
        }
    }

    /**
     * 设置标题栏文字，只有在isUpdateTitle为false时有用
     */
    public void setTitleText(int textRes) {
        if (!isUpdateTitle) {
            titleText.setText(textRes);
        }
    }

    /**
     * 设置标题栏是否隐藏
     */
    public void setTitleVisibility(boolean isVisible) {
        if (isVisible) {
            titleView.setVisibility(View.VISIBLE);
        } else {
            titleView.setVisibility(View.GONE);
        }
    }

    /**
     * 加载网页
     * created by ydy on 2016/7/15 10:14
     */
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public void loadDataWithBaseURL(String baseUrl, String data,
                                    String mimeType, String encoding, String historyUrl) {
        webView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    public void setWebViewCallBack(WebViewCallBack callBack) {
        this.callBack = callBack;
    }

    public interface WebViewCallBack {
        void backOnclick();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}