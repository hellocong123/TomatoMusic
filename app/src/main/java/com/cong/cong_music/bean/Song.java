package com.cong.cong_music.bean;

import java.util.List;

/**
 * @author Cong
 * @date 2018/9/7
 * @description
 */
public class Song {


    private AlbumBean album;
    private ArtistBean artist;
    private String banner;
    private int clicks_count;
    private String created_at;
    private int id;
    private String title;
    private String uri;
    private Long comments_count;

    public Long getComments_count() {
        return comments_count;
    }

    public void setComments_count(Long comments_count) {
        this.comments_count = comments_count;
    }

    public AlbumBean getAlbum() {
        return album;
    }

    public void setAlbum(AlbumBean album) {
        this.album = album;
    }

    public ArtistBean getArtist() {
        return artist;
    }

    public void setArtist(ArtistBean artist) {
        this.artist = artist;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public static class AlbumBean {
        /**
         * id : 1
         * title : 专辑1
         */

        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ArtistBean {
        /**
         * avatar : http://q.qlogo.cn/qqapp/101430237/CB856508131D00E815AF91192E0C06F6/100
         * id : 1
         * nickname : 叶启田
         */

        private String avatar;
        private int id;
        private String nickname;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
