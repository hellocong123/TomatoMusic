package com.cong.cong_music.api;

import com.cong.cong_music.User;
import com.cong.cong_music.bean.Advertisement;
import com.cong.cong_music.bean.ListResponse;
import com.cong.cong_music.bean.Session;
import com.cong.cong_music.bean.Song;
import com.cong.cong_music.bean.SongList;
import com.cong.cong_music.bean.response.DetailResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author Cong
 * @date 2018/8/22
 * @description
 */
public interface ApiService {

    //http://api-dev-courses-misuc.ixuea.com/v1/assets/s2.jpg
    //http://dev-courses-misuc.ixuea.com/%s/assets/s2.jpg
    //http://api-dev-courses-misuc.ixuea.com/v1/sheets.json
    //http://api-dev-courses-misuc.ixuea.com/v1/songs.json
    //http://api-dev-courses-misuc.ixuea.com/v1/advertisements.json
    //http://api-dev-courses-misuc.ixuea.com/v1/sheets/2.json

    //登录请求方法
    @POST("sessions.json")
    Observable<DetailResponse<Session>> login(@Body User user);

    //退出请求方法
    @DELETE("sessions/{id}.json")
    Observable<DetailResponse<Session>> logout(@Path("id") String id);

    //注册，注册完成后返回是登陆的信息
    @POST("users.json")
    Observable<DetailResponse<Session>> register(@Body User user);

    //获取用户详情
    @GET("users/{id}.json")
    Observable<DetailResponse<User>> userDetail(@Path("id") String id);

    //根据nickname,获取用户详情
    @GET("users/-1.json")
    Observable<DetailResponse<User>> userDetailByNickname(@QueryMap Map<String, String> data);

    //歌单列表
    @GET("sheets.json")
    Observable<ListResponse<SongList>> lists(@QueryMap Map<String, String> data);

    //单曲列表
    @GET("songs.json")
    Observable<ListResponse<Song>> songs();

    //广告列表
    @GET("advertisements.json")
    Observable<ListResponse<Advertisement>> advertisements();

    //获取歌单详情
    @GET("sheets/{id}.json")
    Observable<DetailResponse<SongList>> listDetail(@Path("id") String id);
}
