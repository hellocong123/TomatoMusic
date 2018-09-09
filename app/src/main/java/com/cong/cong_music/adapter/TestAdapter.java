package com.cong.cong_music.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cong.cong_music.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cong
 * @date 2018/9/9
 * @description
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private final Context context;
    private List<String> list;


    public TestAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("我是测试数据——————" + i);
        }
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.test, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        String s = list.get(position);

        holder.tv.setText(s);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv);
        }

        public <T extends View> T getView(int id) {

            View view = itemView.findViewById(id);

            return (T) view;
        }
    }
}
