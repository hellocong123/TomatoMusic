package com.cong.cong_music.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cong.cong_music.Consts;
import com.cong.cong_music.R;
import com.cong.cong_music.activity.UserDetailActivity;
import com.cong.cong_music.activity.base.BaseActivity;
import com.cong.cong_music.bean.Comment;
import com.cong.cong_music.listener.OnTagClickListener;
import com.cong.cong_music.util.ImageUtil;
import com.cong.cong_music.util.LogUtil;
import com.cong.cong_music.util.TagUtil;
import com.cong.cong_music.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Cong
 * @date 2018/10/8
 * @description
 */
public class CommentAdapter<T> extends RecyclerView.Adapter {


    private static final int TYPE_TITLE = 0;
    private static final int TYPE_COMMENT = 1;
    private final Context context;


    private List<T> datas = new ArrayList<>();

    private final LayoutInflater inflater;

    public CommentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_TITLE == viewType) {
            return new TitleViewHolder(inflater.inflate(R.layout.item_comment_title, parent, false));
        }
        return new CommentViewHolder(inflater.inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        T t = datas.get(position);

        if (t instanceof String) {
            ((TitleViewHolder) holder).bindData(t);

        } else {
            ((CommentViewHolder) holder).bindData(t);

        }

        // 条目点击事件
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {

        T t = datas.get(position);

        if (t instanceof String) {

            return TYPE_TITLE;
        } else {
            return TYPE_COMMENT;
        }

    }

    public void setData(List<T> data) {
        datas.clear();
        datas.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(T t){
        this.datas.add(t);
        notifyItemInserted(datas.size()-1);
        //notifyDataSetChanged();
    }

    private OnItemClickListener onItemClickListener;

    public T getData(int position) {
        return datas.get(position);
    }



    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(T t) {

            tvTitle.setText(t.toString());
        }
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_avatar;                          //用户头像
        private final ImageView iv_like;
        private final TextView tv_nickname;                         //昵称
        private final TextView tv_like_count;                       //点赞数量
        private final TextView tv_time;                             //时间
        private final TextView tv_content;
        private final TextView tv_reply_content;
        private final LinearLayout ll_like_container;
        private final CardView cv_reply_container;

        public CommentViewHolder(View itemView) {
            super(itemView);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            iv_like = (ImageView) itemView.findViewById(R.id.iv_like);
            tv_nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            tv_like_count = (TextView) itemView.findViewById(R.id.tv_like_count);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_reply_content = (TextView) itemView.findViewById(R.id.tv_reply_content);

            ll_like_container = (LinearLayout) itemView.findViewById(R.id.ll_like_container);
            cv_reply_container = (CardView) itemView.findViewById(R.id.cv_reply_container);
        }

        public void bindData(T t) {

            final Comment c = (Comment) t;

            ImageUtil.showCircle((Activity) context, iv_avatar, c.getUser().getAvatar());
            tv_nickname.setText(c.getUser().getNickname());
            tv_time.setText(TimeUtil.dateTimeFormat1(((Comment) t).getCreated_at()));
            tv_like_count.setText(String.valueOf(c.getLikes_count()));

            //设置后才可以点击
            tv_content.setMovementMethod(LinkMovementMethod.getInstance());
            //评论内容，匹配出和#和@相关的内容，进行高亮和有@的设置点击事件
            tv_content.setText(TagUtil.process(context, c.getContent(), new OnTagClickListener() {
                @Override
                public void onTagClick(String content) {
                    processTagClick(content);
                    LogUtil.d("评论：" + content);
                }
            }));

            if (c.isLiked()) {
                iv_like.setImageResource(R.drawable.ic_comment_liked);
                tv_like_count.setTextColor(context.getResources().getColor(R.color.main_color));
            } else {
                iv_like.setImageResource(R.drawable.ic_comment_like);
                tv_like_count.setTextColor(context.getResources().getColor(R.color.text_dark));
            }

            //回复评论
            if (c.getParent() == null) {
                cv_reply_container.setVisibility(View.GONE);
            } else {
                cv_reply_container.setVisibility(View.VISIBLE);

                tv_reply_content.setMovementMethod(LinkMovementMethod.getInstance());
                tv_reply_content.setLinkTextColor(context.getResources().getColor(R.color.text_Highlight));
                tv_reply_content.setText(TagUtil.process(context, context.getString(R.string.reply_comment_show, c.getParent().getUser().getNickname(), c.getParent().getContent()), new OnTagClickListener() {
                    @Override
                    public void onTagClick(String content) {
                        processTagClick(content);
                    }
                }));

            }

            //把点赞的点击事件放到外面来
            ll_like_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCommentAdapter != null) {
                        onCommentAdapter.onLikeClick(c);
                    }
                }
            });
        }
    }

    private OnCommentAdapter onCommentAdapter;

    public interface OnCommentAdapter{
        void onLikeClick(Comment comment);
    }
    public void setOnCommentAdapter(OnCommentAdapter onCommentAdapter) {
        this.onCommentAdapter = onCommentAdapter;
    }

    private void processTagClick(String content) {
        Log.d("TAG", "processTagClick: " + content);
        if (content.startsWith(Consts.MENTION)) {
            //用户详情界面
            Intent intent = new Intent(context, UserDetailActivity.class);
            intent.putExtra(Consts.NICKNAME, TagUtil.removeTag(content));
            ((BaseActivity) context).startActivity(intent);
        } else if (content.startsWith(Consts.HAST_TAG)) {
            //话题详情
//            Intent intent = new Intent(context, TopicDetailActivity.class);
//            intent.putExtra(Consts.TITLE, TagUtil.removeTag(content));
//            ((BaseActivity)context).startActivity(intent);
        }
    }
}
