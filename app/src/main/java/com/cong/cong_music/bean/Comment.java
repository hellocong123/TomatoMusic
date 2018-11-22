package com.cong.cong_music.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Cong
 * @date 2018/10/8
 * @description
 */
public class Comment {

    private String id;
    private String content;
    private long likes_count;
    private String created_at;


    private int style;                      //区分评论来源，0:视频,10:单曲,20:专辑,30:歌单
    private String sheet_id;                //歌单Id，发布评论用户
    private String like_id;                 //是否点赞，有值表示点赞，null表示没点赞
    private String parent_id;               //回复评论的Id
    private Comment parent;                 //回复的评论，服务端返回数据




    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    private User user;

    public String getSheet_id() {
        return sheet_id;
    }

    public void setSheet_id(String sheet_id) {
        this.sheet_id = sheet_id;
    }

    public String getLike_id() {
        return like_id;
    }

    public void setLike_id(String like_id) {
        this.like_id = like_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(long likes_count) {
        this.likes_count = likes_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLiked() {
        return StringUtils.isNotBlank(like_id);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", likes_count=" + likes_count +
                ", created_at='" + created_at + '\'' +
                ", style=" + style +
                ", sheet_id='" + sheet_id + '\'' +
                ", like_id='" + like_id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", parent=" + parent +
                ", user=" + user +
                '}';
    }
}
