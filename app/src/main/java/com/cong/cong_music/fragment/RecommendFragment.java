package com.cong.cong_music.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cong.cong_music.R;
import com.cong.cong_music.adapter.BaseRecyclerViewAdapter;
import com.cong.cong_music.adapter.RecommendAdapter;
import com.cong.cong_music.api.ApiService;
import com.cong.cong_music.api.RetrofitUtils;
import com.cong.cong_music.bean.Advertisement;
import com.cong.cong_music.bean.ListResponse;
import com.cong.cong_music.bean.Song;
import com.cong.cong_music.bean.SongList;
import com.cong.cong_music.reactivex.HttpListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Cong
 * @date 2018/8/27
 * @description
 */
public class RecommendFragment extends BaseCommonFragment {

    private RecyclerView rv;
    private RecommendAdapter adapter;

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

        adapter = new RecommendAdapter(getActivity());
        rv.setAdapter(adapter);




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
}
