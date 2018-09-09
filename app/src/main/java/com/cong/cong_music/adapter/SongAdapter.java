package com.cong.cong_music.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.cong.cong_music.App;
import com.cong.cong_music.R;
import com.cong.cong_music.bean.Song;
import com.cong.cong_music.bean.SongList;
import com.cong.cong_music.util.ToastUtil;
import com.cong.cong_music.widget.SongMoreDialogFragment;

/**
 * @author Cong
 * @date 2018/9/8
 * @description
 */
public class SongAdapter extends BaseQuickRecyclerViewAdapter<Song> {

    private final FragmentManager fragmentManager;
    private OnSongListener onSongListener;

    public SongAdapter(Context context, int layoutId, FragmentManager fragmentManager) {
        super(context, layoutId);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void bindData(ViewHolder holder, int position, final Song data) {
        holder.setTextView(R.id.tv_title, data.getTitle());
        holder.setTextView(R.id.tv_position, position+1 + "");
        holder.setTextView(R.id.tv_info, data.getArtist().getNickname());

        //更多按钮点击事件
        holder.setOnClickListener(R.id.iv_more, new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SongMoreDialogFragment.show(fragmentManager,data, new SongMoreDialogFragment.OnMoreListener() {
                    @Override
                    public void onCollectionClick(Song song) {
                        ToastUtil.showSortToast(App.getContext(),"收藏");
                    }

                    @Override
                    public void onDownloadClick(Song song) {
                        ToastUtil.showSortToast(App.getContext(),"下载");
                    }

                    @Override
                    public void onDeleteClick(Song song) {
                        ToastUtil.showSortToast(App.getContext(),"删除");
                    }
                });
            }
        });
    }



    public void setOnSongListener(OnSongListener onSongListener) {
        this.onSongListener = onSongListener;
    }


    public interface OnSongListener {
        void onCollectionClick(Song song);//点击收藏

        void onDownloadClick(Song song);//点击下载

        void onDeleteClick(Song song);//点击删除
    }
}
