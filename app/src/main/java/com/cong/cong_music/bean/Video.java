package com.cong.cong_music.bean;

/**
 * @author Cong
 * @date 2018/10/10
 * @description
 */
public class Video {


    /**
     * id : 9
     * title : 李宗盛《给自己的歌》Live，想得却不可得，你奈人生何？
     * banner : assets/s1.jpg
     * uri : assets/1.mp4
     * duration : 40234
     * user : {"id":1,"nickname":"smile","avatar":"http://q.qlogo.cn/qqapp/101430237/CB856508131D00E815AF91192E0C06F6/100"}
     * clicks_count : 0
     * created_at : 2018-06-17T07:57:21.000Z
     */

    private String id;
    private String title;
    private String banner;
    private String uri;
    private int duration;
    private User user;
    private int clicks_count;
    private String created_at;

    private long likes_count;
    private long comments_count;

    public long getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(long likes_count) {
        this.likes_count = likes_count;
    }

    public long getComments_count() {
        return comments_count;
    }

    public void setComments_count(long comments_count) {
        this.comments_count = comments_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getClicks_count() {
        return clicks_count;
    }

    public void setClicks_count(int clicks_count) {
        this.clicks_count = clicks_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


}

