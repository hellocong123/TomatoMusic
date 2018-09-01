package com.cong.cong_music.api;

import com.cong.cong_music.Consts;
import com.cong.cong_music.User;
import com.cong.cong_music.bean.Session;
import com.cong.cong_music.bean.response.DetailResponse;
import com.cong.cong_music.interceptor.HttpLoggingInterceptor;
import com.cong.cong_music.util.LogUtil;
import com.cong.cong_music.util.SharedPreferencesUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Cong
 * @date 2018/8/22
 * @description
 */
public class RetrofitUtils {

    private static final String SONG_ID = "song_id";
    private static RetrofitUtils instance;
    private static ApiService apiService;

    RetrofitUtils() {
        // 创建 OKHttpClient
        //File file = new File(App.getContext().getCacheDir(), "http");
        //创建缓存
        //Cache cache = new Cache(file, 1024 * 1024 * 1000);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //builder.cache(cache);
        builder.connectTimeout(Consts.TIME_OUT, TimeUnit.SECONDS);//连接 超时时间
        builder.writeTimeout(Consts.TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(Consts.TIME_OUT, TimeUnit.SECONDS);//读操作 超时时间

        if (LogUtil.isDebug) {

            builder.addInterceptor(new HttpLoggingInterceptor());//主要是看控制台的，放便开发的时候看Http的请求
            builder.addNetworkInterceptor(new StethoInterceptor());
//            builder.addInterceptor(new ChuckInterceptor(App.getContext()));
        }

        //用对网络请求缓存，详细的查看《详解OKHttp》课程
        //builder.addInterceptor(FORCE_CACHE_NETWORK_DATA_INTERCEPTOR);
        //builder.addNetworkInterceptor(FORCE_CACHE_NETWORK_DATA_INTERCEPTOR);

        //公共请求参数
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //获取SP实例
                SharedPreferencesUtil sp = SharedPreferencesUtil.getCurrentInstance();

                Request request = chain.request();
                //判断用户是否登录了，
                if (sp.isLogin()) {
                   //从SP里获取到登录那里保存的 userId/token
                    String userId = sp.getUserId();
                    String token = sp.getToken();
                    //如果使用的是未上线端口，就可以打印
                    if (LogUtil.isDebug) {
                        LogUtil.d("token:" + token + "," + userId);
                    }
                    //如果是登录了就给每一个请求都添加一个头，一个是用户id,一个是token
                    request = chain.request().newBuilder()
                            .addHeader("User", userId)
                            .addHeader("Authorization", token)
                            .build();
                }
                return chain.proceed(request);
            }
        });

       //Retrofit实例化，并添加上面的OkHttp到里面
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Consts.RESOURCE_PREFIX)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RxJava适配器
                .addConverterFactory(GsonConverterFactory.create())//添加Gson转换器
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    //单例获取实例
    public static RetrofitUtils getInstance() {
        if (instance == null) {
            instance = new RetrofitUtils();
        }
        return instance;
    }

    //定义登录方法，在这个方法里面使用service调用ApiService里面的请求方法，就可以返回一个Observable
    public Observable<DetailResponse<Session>> login(User user) {
        //调用接口Api里的方法返回一个Observable
        return apiService.login(user);
    }

    public Observable<DetailResponse<Session>> register(User user) {
        return apiService.register(user);
    }

    public Observable<DetailResponse<Session>> logout(String id) {
        return apiService.logout(id);
    }


    public Observable<DetailResponse<User>> userDetail(String id) {
        return apiService.userDetail(id);
    }

    public Observable<DetailResponse<User>> userDetailByNickname(String nickname) {
        HashMap<String, String> data = new HashMap<>();
        data.put(Consts.NICKNAME, nickname);
        return apiService.userDetailByNickname(data);
    }

//    public Observable<ListResponse<List>> lists() {
//        HashMap<String, String> query = new HashMap<>();
//        return apiService.lists(query);
//    }
//
//    public Observable<ListResponse<SearchHot>> prompt(String content) {
//        HashMap<String, String> query = new HashMap<>();
//        query.put(Consts.TITLE,content);
//        return apiService.prompt(query);
//    }
//
//    public Observable<ListResponse<SearchHot>> searchHot() {
//        HashMap<String, String> query = new HashMap<>();
//        return apiService.searchHot(query);
//    }
//
//    public Observable<ListResponse<Song>> searchSong(String title) {
//        HashMap<String, String> query = new HashMap<>();
//        query.put(Consts.TITLE,title);
//        return apiService.searchSong(query);
//    }
//
//    public Observable<DetailResponse<List>> createList(List list) {
//        return apiService.createList(list);
//    }
//
//    public Observable<DetailResponse<Comment>> createComment(Comment comment) {
//        return apiService.createComment(comment);
//    }
//
//    public Observable<DetailResponse<List>> collectionList(String listId) {
//        return apiService.collectionList(listId);
//    }
//
//    public Observable<DetailResponse<Comment>> like(String commentId) {
//        return apiService.like(commentId);
//    }
//
//    public Observable<DetailResponse<User>> follow(String userId) {
//        return apiService.follow(userId);
//    }
//
//    public Observable<DetailResponse<User>> unFollow(String userId) {
//        return apiService.unFollow(userId);
//    }
//
//    public Observable<DetailResponse<List>> cancelCollectionList(String id) {
//        return apiService.cancelCollectionList(id);
//    }
//
//    public Observable<DetailResponse<Comment>> unlike(String id) {
//        return apiService.unlike(id);
//    }
//
//    public Observable<DetailResponse<List>> addSongInSheet(String songId,String listId) {
//        return apiService.addSongInSheet(songId,listId);
//    }
//
//    public Observable<DetailResponse<List>> deleteSongInSheet(String songId,String sheetId) {
//        return apiService.deleteSongInSheet(songId,sheetId);
//    }
//
//    public Observable<ListResponse<List>> listsMyCreate() {
//        return apiService.listsMyCreate();
//    }
//
//    public Observable<ListResponse<List>> listsMyCollection() {
//        return apiService.listsMyCollection();
//    }
//
//    public Observable<ListResponse<Song>> songs() {
//        return apiService.songs();
//    }
//
//    public Observable<DetailResponse<Song>> songsDetail(String id) {
//        return apiService.songsDetail(id);
//    }
//
//    public Observable<DetailResponse<List>> listDetail(String id) {
//        return apiService.listDetail(id);
//    }
//
//    //public Observable<ListResponse<Feed>> feedsByTopic(String topic) {
//    //    HashMap<String, String> data = new HashMap<>();
//    //    data.put(Consts.TOPIC,topic);
//    //    return apiService.feeds(data);
//    //}
//    //
//    ///**
//    // * 获取动态，传UserId数据就是该用户的，不传就是全部
//    // * @param userId
//    // * @return
//    // */
//    public Observable<ListResponse<Feed>> feeds(String userId, int pageSize) {
//        HashMap<String, String> data = new HashMap<>();
//        if (StringUtils.isNotBlank(userId)) {
//            data.put(Consts.USER_ID,userId);
//        }
//        data.put(Consts.PAGE,String.valueOf(pageSize));
//        return apiService.feeds(data);
//    }
//
//    public Observable<DetailResponse<Feed>> createFeed(FeedParam data) {
//        return apiService.createFeed(data);
//    }
//
//    public Observable<ListResponse<Comment>> comments(Map<String,String> data) {
//        return apiService.comments(data);
//    }
//
//    public Observable<ListResponse<User>> myFriends(String id,String nickname) {
//        HashMap<String, String> data = new HashMap<>();
//
//        //根据nickname查找
//        if (StringUtils.isNotEmpty(nickname)) {
//            data.put(Consts.FILTER, nickname);
//        }
//        return apiService.following(id,data);
//    }
//
//    public Observable<ListResponse<User>> myFans(String id,String nickname) {
//        HashMap<String, String> data = new HashMap<>();
//
//        //根据nickname查找
//        if (StringUtils.isNotEmpty(nickname)) {
//            data.put(Consts.FILTER, nickname);
//        }
//        return apiService.followers(id,data);
//    }
//
//    public Observable<ListResponse<Topic>> topics(String title) {
//        HashMap<String, String> data = new HashMap<>();
//        if (StringUtils.isNotEmpty(title)) {
//            data.put(Consts.FILTER, title);
//        }
//        return apiService.topics(data);
//    }
//
//    public Observable<DetailResponse<Topic>> topicDetail(String id) {
//        HashMap<String, String> data = new HashMap<>();
//        return apiService.topicDetail(id,data);
//    }
//
//    public Observable<DetailResponse<Topic>> topicDetailByTitle(String title) {
//        HashMap<String, String> data = new HashMap<>();
//        data.put(Consts.TITLE,title);
//        return apiService.topicDetail(String.valueOf(-1),data);
//    }
//
//    public Observable<ListResponse<Video>> videos() {
//        HashMap<String, String> data = new HashMap<>();
//        return apiService.videos(data);
//    }
//
//    public Observable<DetailResponse<Video>> videoDetail(String id) {
//        return apiService.videoDetail(id);
//    }
//
//    public Observable<ListResponse<Advertisement>> advertisements() {
//        return apiService.advertisements();
//    }

    //public Observable<DetailResponse<Lyric>>  lyricDetailWithBySongId(String id) {
    //    HashMap<String, String> data = new HashMap<>();
    //    data.put(SONG_ID,id);
    //    return apiService.lyricDetailWithBySongId(data);
    //}
}
