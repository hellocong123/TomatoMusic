package com.cong.cong_music.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cong.cong_music.R;

import java.util.List;

/**
 * @author Cong
 * @date 2018/10/9
 * @description
 */
public class UserAdapter extends BaseRecycleView<String> {

    public UserAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindData(String s, BaseViewHolder holder) {

        TextView tv = holder.getView(R.id.tv);
        tv.setText(s);
    }



    @Override
    public int layoutId() {
        return R.layout.test;
    }


}
