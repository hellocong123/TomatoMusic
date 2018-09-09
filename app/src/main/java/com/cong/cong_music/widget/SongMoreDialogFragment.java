package com.cong.cong_music.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cong.cong_music.R;
import com.cong.cong_music.bean.Song;

/**
 * @author Cong
 * @date 2018/9/9
 * @description
 */
public class SongMoreDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView tv_song;
    private TextView tv_comment_count;
    private TextView tv_album;
    private TextView tv_artist;
    private LinearLayout ll_next_play;
    private LinearLayout ll_collection;
    private LinearLayout ll_download;
    private LinearLayout ll_comment;
    private LinearLayout ll_delete;
    private boolean isShowDelete;
    private Song song;


    private OnMoreListener moreListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_song_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_song = view.findViewById(R.id.tv_song);
        ll_next_play = view.findViewById(R.id.ll_next_play);
        ll_collection = view.findViewById(R.id.ll_collection);
        ll_download = view.findViewById(R.id.ll_download);
        ll_comment = view.findViewById(R.id.ll_comment);
        tv_comment_count = view.findViewById(R.id.tv_comment_count);
        tv_album = view.findViewById(R.id.tv_album);
        tv_artist = view.findViewById(R.id.tv_artist);
        ll_delete = view.findViewById(R.id.ll_delete);

        if (isShowDelete) {
            ll_delete.setVisibility(View.VISIBLE);
        }

        initData();
        initListener();

    }

    private void initListener() {
        ll_next_play.setOnClickListener(this);
        ll_collection.setOnClickListener(this);
        ll_download.setOnClickListener(this);
        ll_delete.setOnClickListener(this);

    }

    private void initData() {

        tv_song.setText(getContext().getResources().getString(R.string.song_detail, song.getTitle()));                      //歌名
        tv_comment_count.setText(getContext().getResources().getString(R.string.comment_count, song.getComments_count()));  //评论数
        tv_artist.setText(getContext().getResources().getString(R.string.artist, song.getArtist().getNickname()));          //歌手
        tv_album.setText(getContext().getResources().getString(R.string.album, song.getAlbum().getTitle()));                //专辑

    }

    public static void show(FragmentManager fragmentManager, Song song,OnMoreListener moreListener ) {

        SongMoreDialogFragment fragment = new SongMoreDialogFragment();
        fragment.setData(song);
        fragment.setOnMoreListener(moreListener);
        fragment.show(fragmentManager, "dialog");
    }

    public void setData(Song data) {
        this.song = data;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_next_play:
                this.dismiss();
                //playListManager.nextPlay(song);
                break;

            case R.id.ll_collection:
                this.dismiss();
                if (moreListener != null) {
                    moreListener.onCollectionClick(song);
                }
                break;

            case R.id.ll_download:
                this.dismiss();
                if (moreListener != null) {
                    moreListener.onDownloadClick(song);
                }
                break;

            case R.id.ll_delete:
                this.dismiss();
                //playListManager.delete(song);
                if (moreListener != null) {
                    moreListener.onDeleteClick(song);
                }
                break;
        }
    }


    public void setOnMoreListener(OnMoreListener moreListener) {
        this.moreListener = moreListener;
    }

    public interface OnMoreListener {
        void onCollectionClick(Song song);

        void onDownloadClick(Song song);

        void onDeleteClick(Song song);
    }
}
