package com.cong.cong_music.bean;

/**
 * @author Cong
 * @date 2018/9/7
 * @description
 */
public class Advertisement {

    private String banner;      //图片地址
    private String uri;         //点击跳转的页面
    private String title;       //广告标题

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
