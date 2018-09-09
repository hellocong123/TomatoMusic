package com.cong.cong_music.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cong.cong_music.R;
import com.cong.cong_music.bean.Advertisement;
import com.cong.cong_music.bean.Song;
import com.cong.cong_music.bean.SongList;
import com.cong.cong_music.util.ImageUtil;
import com.cong.cong_music.util.LogUtil;
import com.cong.cong_music.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cong
 * @date 2018/9/7
 * @description
 */
public class RecommendAdapter extends BaseRecyclerViewAdapter<Object, RecommendAdapter.BaseViewHolder> {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_LIST = 1;
    public static final int TYPE_SONG = 2;
    public static final int TYPE_ADVERTISEMENT = 3;
    private ArrayList<Object> data;

    public RecommendAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecommendAdapter.BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_TITLE:
                return new TitleViewHolder(inflater.inflate(R.layout.item_title, parent, false));
            case TYPE_LIST:
                return new ListViewHolder(inflater.inflate(R.layout.item_list, parent, false));
            case TYPE_SONG:
                return new SongViewHolder(inflater.inflate(R.layout.item_song, parent, false));
        }
        return new AdvertisementViewHolder(inflater.inflate(R.layout.item_advertisement, parent, false));
    }
    //自己复写父类里的onBindViewHolder方法，父类里的方法是用来处理点击事件监听
    @Override
    public void onBindViewHolder(@NonNull RecommendAdapter.BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        //绑定数据直接调用bindData方法
        //具体的每个类自己实现，爱咋地就咋地
        holder.bindData(getData(position));
    }

    public abstract class BaseViewHolder extends BaseRecyclerViewAdapter.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
        public abstract void bindData(Object data);
    }


    @Override
    public int getItemViewType(int position) {
        Object data = getData(position);

        if (data instanceof String) {
            //标题
            return TYPE_TITLE;
        } else if (data instanceof SongList) {
            //歌单
            return TYPE_LIST;
        } else if (data instanceof Song) {
            //单曲
            return TYPE_SONG;
        }
        //广告
        return TYPE_ADVERTISEMENT;
    }


    @Override
    public int setSpanSizeLookup(int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case RecommendAdapter.TYPE_TITLE:
                return 3;
            case RecommendAdapter.TYPE_LIST:
                return 1;
            case RecommendAdapter.TYPE_SONG:
                return 3;
        }
        return 3;
    }

    /**
     * 标题
     */
    private class TitleViewHolder extends BaseViewHolder {
        private final TextView tv_title;
        public TitleViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) findViewById(R.id.tv_title);
        }
        @Override
        public void bindData(Object data) {
            tv_title.setText(data.toString());
        }
    }

    /**
     * 歌单列表
     */
    private class ListViewHolder extends BaseViewHolder {

        private ImageView iv_banner;
        private TextView tv_count;
        private TextView tv_title;

        public ListViewHolder(View itemView) {
            super(itemView);
            iv_banner = (ImageView) findViewById(R.id.iv_banner);
//            tv_count = (TextView) findViewById(R.id.tv_count);
            tv_title = (TextView) findViewById(R.id.tv_title);
        }

        @Override
        public void bindData(Object data) {
            SongList list = (SongList) data;
            ImageUtil.show((Activity) context, iv_banner, list.getBanner());
//            tv_count.setText(StringUtil.formatCount(list.getClicks_count()));
            tv_title.setText(list.getTitle());
        }
    }

    /**
     * 歌曲
     */
    private class SongViewHolder extends BaseViewHolder {

        private final ImageView iv_avatar;
        private final TextView tv_nickname;
        private ImageView iv_icon;
        private TextView tv_title;
        private TextView tv_comment_count;

        public SongViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) findViewById(R.id.iv_icon);
            iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
            tv_title = (TextView) findViewById(R.id.tv_title);
            tv_nickname = (TextView) findViewById(R.id.tv_nickname);
            tv_comment_count = (TextView) findViewById(R.id.tv_comment_count);
        }

        @Override
        public void bindData(Object data) {
            Song d = (Song) data;
            ImageUtil.show((Activity) context, iv_icon, d.getBanner());
            tv_title.setText(d.getTitle());
            tv_nickname.setText(d.getArtist().getNickname());
//            tv_comment_count.setText(StringUtil.formatCount(d.getClicks_count()));

            ImageUtil.show((Activity) context, iv_avatar, d.getArtist().getAvatar());
        }
    }

    /**
     * AD广告
     */
    private class AdvertisementViewHolder extends BaseViewHolder {
        private ImageView iv_icon;
        private TextView tv_title;

        public AdvertisementViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) findViewById(R.id.iv_icon);
            tv_title = (TextView) findViewById(R.id.tv_title);
        }

        @Override
        public void bindData(Object data) {
            Advertisement d = (Advertisement) data;
            ImageUtil.show((Activity) context, iv_icon, d.getBanner());
            tv_title.setText(d.getTitle());
        }
    }





}
