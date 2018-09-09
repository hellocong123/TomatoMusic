package com.cong.cong_music.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cong
 * @date 2018/9/7
 * @description
 */
public abstract class BaseRecyclerViewAdapter<D, VH extends BaseRecyclerViewAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context context;
    public LayoutInflater inflater;
    private List<D> datas = new ArrayList<>();

    private OnItemClickListener onItemClickListener;            //条目点击接口对象
    private OnItemLongClickListener onItemLongClickListener;    //条目长按接口对象
    private ArrayList<Object> data;

    public BaseRecyclerViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder, position);
                }
            });


            if (onItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return onItemLongClickListener.onItemLongClick(holder, position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //获取RecycledView里的数据集
    public D getData(int position) {
        return datas.get(position);
    }

    //添加数据到RecyclerView的集合里面
    public void addData(List<D> data) {
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    //给Adapter设置数据
    public void setData(List<D> data) {
        this.datas.clear();
        this.datas.addAll(data);
        //notifyItemRangeChanged(0, data.size());
        notifyDataSetChanged();
    }

    //点击条目监听接口
    public interface OnItemClickListener {
        void onItemClick(BaseRecyclerViewAdapter.ViewHolder holder, int position);
    }

    //条目长按点击监听接口
    public interface OnItemLongClickListener {
        boolean onItemLongClick(BaseRecyclerViewAdapter.ViewHolder holder, int position);
    }

    public int setSpanSizeLookup(int position) {
        return 1;
    }



    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }


        public <T extends View> T findViewById(int id) {
            return itemView.findViewById(id);
        }
    }

}
