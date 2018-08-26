package com.cong.cong_music.activity.base;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import com.cong.cong_music.R;

/**
 * @author Cong
 * @date 2018/8/23
 * @description
 */
public class BaseToolBarActivity extends BaseCommonActivity {

    private Toolbar toolbar;

    @Override
    protected void initViews() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }



    @Override
    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            super.setTitle(title);
        }
    }

    protected void enableBackMenu() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    //给导航按钮，增加返回的功能
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
