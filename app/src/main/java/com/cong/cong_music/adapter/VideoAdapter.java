package com.cong.cong_music.adapter;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.cong.cong_music.R;
import com.cong.cong_music.bean.Video;
import com.cong.cong_music.demo.BaseRecycleView;
import com.cong.cong_music.util.ImageUtil;
import com.cong.cong_music.util.TimeUtil;

/**
 * @author Cong
 * @date 2018/10/10
 * @description
 */
public class VideoAdapter extends BaseRecycleView<Video>{

    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindData(Video video, BaseRecycleView.BaseViewHolder holder) {

        ImageUtil.show((Activity) context,(ImageView) holder.getView(R.id.iv_icon),video.getBanner());
        ImageUtil.showCircle((Activity) context,(ImageView) holder.getView(R.id.iv_avatar),video.getUser().getAvatar());

        holder.setText(R.id.tv_nickname,video.getUser().getNickname());
        holder.setText(R.id.tv_title,video.getTitle());
        holder.setText(R.id.tv_clicks_count,String.valueOf(video.getClicks_count()));
        holder.setText(R.id.tv_time, TimeUtil.formatMSTime((int) video.getDuration()));
        holder.setText(R.id.tv_likes_count, String.valueOf(video.getLikes_count()));
        holder.setText(R.id.tv_comments_count, String.valueOf(video.getComments_count()));
    }

    @Override
    public int layoutId() {
        return R.layout.item_video;
    }
}
