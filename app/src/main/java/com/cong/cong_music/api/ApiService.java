package com.cong.cong_music.api;

import com.cong.cong_music.User;
import com.cong.cong_music.bean.Session;
import com.cong.cong_music.bean.response.DetailResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author Cong
 * @date 2018/8/22
 * @description
 */
public interface ApiService {

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
}
