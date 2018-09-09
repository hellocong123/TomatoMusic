package com.cong.cong_music.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cong.cong_music.R;
import com.cong.cong_music.activity.BaseWebViewActivity;
import com.cong.cong_music.activity.ListDetailActivity;
import com.cong.cong_music.adapter.BaseRecyclerViewAdapter;
import com.cong.cong_music.adapter.RecommendAdapter;
import com.cong.cong_music.api.RetrofitUtils;
import com.cong.cong_music.bean.Advertisement;
import com.cong.cong_music.bean.ListResponse;
import com.cong.cong_music.bean.Song;
import com.cong.cong_music.bean.SongList;
import com.cong.cong_music.reactivex.HttpListener;
import com.cong.cong_music.util.ImageUtil;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Cong
 * @date 2018/8/27
 * @description
 */
public class RecommendFragment extends BaseCommonFragment implements OnBannerListener {

    private LRecyclerView rv;
    private RecommendAdapter adapter;
    private LRecyclerViewAdapter adapterWrapper;
    private Banner banner;
    private LinearLayout ll_day_container;
    private List<Advertisement> bannerData;

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, null);
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        super.initViews();

        rv = findView(R.id.rv);

    }

    @Override
    protected void initDatas() {

        rv.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());


        rv.setLayoutManager(layoutManager);
//        rv.setPullRefreshEnabled(false);                //取消下拉刷新
//        rv.setLoadMoreEnabled(false);                   //取消上拉加载

        adapter = new RecommendAdapter(getActivity());
//        rv.setAdapter(adapter);

//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                //先获取ItemType
//                int itemViewType = adapter.getItemViewType(position);
//                switch (itemViewType) {
//                    case RecommendAdapter.TYPE_TITLE:
//                        //表示这个布局内容要占满一行里的3个格子
//                        return 3;
//                    case RecommendAdapter.TYPE_LIST:
//                        //表示这个布局内容可以占一行里的1个格子，也就是span是多少，就显示多少
//                        return 1;
//                    case RecommendAdapter.TYPE_SONG://推荐单曲
//                        return 3;
//                }
//                //其他情况，表示正常的数据Item，默认显示3列
//                return 3;
//            }
//        });

        adapterWrapper = new LRecyclerViewAdapter(adapter);
        adapterWrapper.setSpanSizeLookup(new LRecyclerViewAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                if (position < adapterWrapper.getHeaderViewsCount() || position > (adapterWrapper.getHeaderViewsCount() + adapter.getItemCount())) {
                    //f当前位置的Item是header，占用列数spanCount一样
                    return ((GridLayoutManager) layoutManager).getSpanCount();
                }
                return adapter.setSpanSizeLookup(position);
            }
        });

        //RecycleView添加头布局
        adapterWrapper.addHeaderView(createHeaderView());

        rv.setAdapter(adapterWrapper);

        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseRecyclerViewAdapter.ViewHolder holder, int position) {
                Object data = adapter.getData(position);

                if (data instanceof Song) {

                } else if (data instanceof SongList) {
                    //歌单
                    startActivityExtraId(ListDetailActivity.class,((SongList) data).getId());

                } else if (data instanceof Advertisement) {

                }
            }
        });

//        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseRecyclerViewAdapter.ViewHolder holder, int position) {
//                //Course course = (Course) adapter.getItem(position);
//                //startActivityExtraId(CourseDetailActivity.class, course.getId());
//            }
//        });
//
//        adapterWrapper = new EnhanceAdapterWrapper(adapter);
//
//        adapterWrapper.addHeaderView(createHeaderView());
//
//
//        rv.setAdapter(adapterWrapper);
//
        fetchData();


    }

    private View createHeaderView() {
        View top = getLayoutInflater().inflate(R.layout.header_music_recommend, (ViewGroup) rv.getParent(), false);
        banner = top.findViewById(R.id.banner);
        banner.setOnBannerListener(this);


        ll_day_container = top.findViewById(R.id.ll_day_container);             //每日推荐布局
        TextView tv_day = top.findViewById(R.id.tv_day);                        //每日推荐里的日期

        //设置日期
        Calendar cal = Calendar.getInstance();                                  //获取日历实例
        int day = cal.get(Calendar.DAY_OF_MONTH);
        tv_day.setText(String.valueOf(day));

        //还有一个3D反转动画，这里就不设置了，详细的查看《详解Animation》课程
        //ll_day_container.setOnClickListener(this);

        return top;
    }

    private void fetchData() {

        Observable<ListResponse<SongList>> lists = RetrofitUtils.getInstance().lists();
        final Observable<ListResponse<Song>> songs = RetrofitUtils.getInstance().songs();
        final Observable<ListResponse<Advertisement>> advertisements = RetrofitUtils.getInstance().advertisements();

        final ArrayList<Object> d = new ArrayList<>();
        d.add("推荐歌单");

        lists.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpListener<ListResponse<SongList>>(getMainActivity()) {
                    @Override
                    public void onSucceeded(ListResponse<SongList> data) {

                        d.addAll(data.getData());
                        songs.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new HttpListener<ListResponse<Song>>(getMainActivity()) {
                                    @Override
                                    public void onSucceeded(ListResponse<Song> data) {

                                        d.add("推荐单曲");
                                        d.addAll(data.getData());
                                        advertisements.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new HttpListener<ListResponse<Advertisement>>(getMainActivity()) {
                                                    @Override
                                                    public void onSucceeded(ListResponse<Advertisement> data) {

                                                        showBanner(data.getData());//首页轮播图广告
                                                        d.addAll(data.getData());
                                                        adapter.setData(d);
                                                    }


                                                });
                                    }
                                });
                    }
                });


    }

    @Override
    protected void initListener() {

    }

    private void showBanner(List<Advertisement> data) {

        this.bannerData = data;

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        banner.setImages(data);
        banner.start();
    }

    //轮播图广告点击事件监听
    @Override
    public void OnBannerClick(int position) {
        Advertisement ad = bannerData.get(position);
        BaseWebViewActivity.start(getMainActivity(), "活动详情", "http://www.ixuea.com");
    }

    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //因为引入了一个Banner控件，所有这里要使用全类名
            Advertisement banner = (Advertisement) path;
            ImageUtil.show(getMainActivity(), imageView, banner.getBanner());
        }
    }
}
