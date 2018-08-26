package com.cong.cong_music.bean;

/**
 * @author Cong
 * @date 2018/8/22
 * @description
 */
public class Session {
    private String id; //当前用户的user id
    private String token;
    /**
     * 聊天用的token
     */
    private String im_token;

    public String getIm_token() {
        return im_token;
    }

    public void setIm_token(String im_token) {
        this.im_token = im_token;
    }

    public String getToken() {
        return token;
    }

    public Session setToken(String token) {
        this.token = token;
        return this;
    }

    public String getId() {
        return id;
    }

    public Session setId(String id) {
        this.id = id;
        return this;
    }
}
