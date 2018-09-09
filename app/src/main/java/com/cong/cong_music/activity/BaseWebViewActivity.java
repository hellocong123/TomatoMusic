package com.cong.cong_music.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cong.cong_music.Consts;
import com.cong.cong_music.R;
import com.cong.cong_music.activity.base.BaseToolBarActivity;

/**
 * @author Cong
 * @date 2018/9/8
 * @description
 */
public class BaseWebViewActivity extends BaseToolBarActivity {

    private WebView wv;

    @Override
    protected void initViews() {


        setContentView(R.layout.activity_base_web_view);

        wv =findViewById(R.id.wv);
        WebSettings webSettings = wv.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
//        webSettings.setAllowUniversalAccessFromFileURLs(true);
//        webSettings.setAllowFileAccessFromFileURLs(true);

        webSettings.setDomStorageEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        super.initViews();

    }
    /**
     * 定义静态的启动方法，好处是用户只要看到声明，就知道该界面需要哪些参数
     * @param activity
     * @param title
     * @param url
     */
    public static void start(Activity activity, String title, String url) {
        Intent intent = new Intent(activity, BaseWebViewActivity.class);
        intent.putExtra(Consts.TITLE, title);
        intent.putExtra(Consts.URL, url);
        activity.startActivity(intent);
    }



    @Override
    protected void initDatas() {
        super.initDatas();

        String url = getIntent().getStringExtra(Consts.URL);

      //  setTitle(title);



        if (!TextUtils.isEmpty(url)) {
            wv.loadUrl(url);
        }  else {
            finish();
        }

        enableBackMenu();
    }

    @Override
    protected void initToolbar() {
        String title = getIntent().getStringExtra(Consts.TITLE);
        toolbar.setTitle(title);
    }
}
