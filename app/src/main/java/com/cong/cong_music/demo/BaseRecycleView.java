package com.cong.cong_music.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cong
 * @date 2018/10/9
 * @description
 */
public abstract class BaseRecycleView<T> extends RecyclerView.Adapter<BaseRecycleView.BaseViewHolder> {

    public List<T> datas;
    public Context context;

    public BaseRecycleView(Context context) {
        this.context = context;
        this.datas = new ArrayList();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId(), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final BaseRecycleView.BaseViewHolder holder, final int position) {

        T t = this.datas.get(position);

        if (onItemClickListener != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder, position);
                }
            });
        }

        bindData(t, holder);
    }

    public abstract void bindData(T t, BaseViewHolder holder);

    public abstract int layoutId();

    public void setData(List<T> data) {
        datas.clear();
        datas.addAll(data);
        notifyDataSetChanged();
    }

    public T getData(int position) {
        return datas.get(position);
    }

    public List<T> getData() {
        return datas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public OnItemClickListener onItemClickListener;

 public   interface OnItemClickListener {
        void onClick(BaseViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> sparseArray;

        public BaseViewHolder(View itemView) {
            super(itemView);
            sparseArray = new SparseArray<>();
        }

        public BaseViewHolder setText(int id, CharSequence value) {
            TextView view = getView(id);
            view.setText(value);
            return this;
        }


        public BaseViewHolder setText(int id, int value) {
            TextView view = getView(id);
            view.setText(value);
            return this;
        }

        public <T extends View> T getView(int id) {
            View view = sparseArray.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                sparseArray.put(id, view);
            }
            return (T) view;
        }
    }
}
