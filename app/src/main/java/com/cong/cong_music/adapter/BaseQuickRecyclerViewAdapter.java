package com.cong.cong_music.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Cong
 * @date 2018/9/8
 * @description
 */
public abstract class BaseQuickRecyclerViewAdapter<D> extends BaseRecyclerViewAdapter<D, BaseQuickRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private int layoutId;

    public BaseQuickRecyclerViewAdapter(Context context, int layoutId) {
        super(context);
        this.context = context;
        this.layoutId = layoutId;
    }


    @NonNull
    @Override
    public BaseQuickRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseQuickRecyclerViewAdapter.ViewHolder(inflater.inflate(layoutId, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull BaseQuickRecyclerViewAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        bindData(holder, position, getData(position));

    }

    public abstract void bindData(ViewHolder holder, int position, D data);

    /**
     * ViewHolder
     * 缓存View
     */
    public class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder {
        private final SparseArray<View> itemViews = new SparseArray<>();

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public <T extends View> T getView(int id) {
            View view = itemViews.get(id);
            if (view == null) {
                view = findViewById(id);
                itemViews.put(id, view);
            }
            return (T) view;
        }

        public ViewHolder setTextView(int id, CharSequence value) {
            TextView view = getView(id);
            view.setText(value);
            return this;
        }

        public void setOnClickListener(int id, View.OnClickListener onClickListener) {
            View view = getView(id);
            view.setOnClickListener(onClickListener);
        }
    }
}
