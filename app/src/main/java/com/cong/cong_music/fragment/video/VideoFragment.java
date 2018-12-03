package com.cong.cong_music.fragment.video;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cong.cong_music.R;
import com.cong.cong_music.activity.VideoDetailActivity;
import com.cong.cong_music.adapter.BaseRecyclerViewAdapter;
import com.cong.cong_music.adapter.VideoAdapter;
import com.cong.cong_music.api.RetrofitUtils;
import com.cong.cong_music.bean.ListResponse;
import com.cong.cong_music.bean.Video;
import com.cong.cong_music.demo.BaseRecycleView;
import com.cong.cong_music.fragment.base.BaseCommonFragment;
import com.cong.cong_music.reactivex.HttpListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.internal.util.NotificationLite.next;

/**
 * @author Cong
 * @date 2018/8/26
 * @description
 */
public class VideoFragment extends BaseCommonFragment {

    private RecyclerView rv;
    private VideoAdapter adapter;

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, null);
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        rv = getView().findViewById(R.id.rv);
        super.initViews();
    }

    @Override
    protected void initDatas() {

        adapter = new VideoAdapter(getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new BaseRecycleView.OnItemClickListener() {
            @Override
            public void onClick(BaseRecycleView.BaseViewHolder holder, int position) {
                Video data = adapter.getData(position);
                startActivityExtraId(VideoDetailActivity.class, data.getId());
            }
        });


        fetchData();
    }


    private void fetchData() {
        RetrofitUtils.getInstance().videos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpListener<ListResponse<Video>>(getMainActivity()) {
                    @Override
                    public void onSucceeded(ListResponse<Video> data) {

                        next(data.getData());
                    }
                });
    }

    public void next(List<Video> data) {

        adapter.setData(data);
    }
}
