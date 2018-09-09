package com.cong.cong_music.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cong.cong_music.Consts;
import com.cong.cong_music.R;
import com.cong.cong_music.activity.base.BaseToolBarActivity;
import com.cong.cong_music.adapter.SongAdapter;
import com.cong.cong_music.api.RetrofitUtils;
import com.cong.cong_music.bean.Song;
import com.cong.cong_music.bean.SongList;
import com.cong.cong_music.bean.response.DetailResponse;
import com.cong.cong_music.reactivex.HttpListener;
import com.cong.cong_music.util.ImageUtil;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListDetailActivity extends BaseToolBarActivity {


    private ImageView iv_icon;//歌单封面图片
    private TextView tv_title;//歌单标题
    private TextView tv_nickname;//歌单用户昵称
    private TextView tv_comment_count;//歌单消息数量
    private LinearLayout header_container;
    private LinearLayout ll_comment_container;
    private LinearLayout ll_play_all_container;
    private RelativeLayout rl_player_container;
    private TextView tv_play_all;
    private TextView tv_count;
    private Button bt_collection;

    private LRecyclerView rv;
    private LRecyclerViewAdapter adapterWrapper;
    private String id;
    private SongAdapter adapter;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_list_detail);
        rv = findView(R.id.rv);
        super.initViews();
        enableBackMenu();

    }


    @Override
    protected void initDatas() {

//        TestAdapter testAdapter = new TestAdapter(getActivity());
//        rv2.setAdapter(testAdapter);
//        rv2.setLayoutManager(new LinearLayoutManager(this));


        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SongAdapter(getActivity(), R.layout.item_song_detail,getSupportFragmentManager());
        adapterWrapper = new LRecyclerViewAdapter(adapter);
        rv.setAdapter(adapterWrapper);
        rv.setPullRefreshEnabled(false);
        rv.setLoadMoreEnabled(false);


        //创建一个RecycleView头布局
        adapterWrapper.addHeaderView(createHeaderView());

        fetchData();

    }

    //头布局
    private View createHeaderView() {
        View top = LayoutInflater.from(getActivity()).inflate(R.layout.header_song_detail, (ViewGroup) rv.getParent(), false);
        header_container = top.findViewById(R.id.header_container);
        ll_comment_container = top.findViewById(R.id.ll_comment_container);
        bt_collection = top.findViewById(R.id.bt_collection);
        ll_play_all_container = top.findViewById(R.id.ll_play_all_container);
        iv_icon = top.findViewById(R.id.iv_icon);
        tv_title = top.findViewById(R.id.tv_title);
        tv_nickname = top.findViewById(R.id.tv_nickname);
        tv_comment_count = top.findViewById(R.id.tv_comment_count);
        tv_play_all = top.findViewById(R.id.tv_play_all);
        tv_count = top.findViewById(R.id.tv_count);
        return top;
    }

    //连网获取数据
    private void fetchData() {
        id = getIntent().getStringExtra(Consts.KEY_ID);
        RetrofitUtils.getInstance().listDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpListener<DetailResponse<SongList>>(getActivity()) {
                    @Override
                    public void onSucceeded(DetailResponse<SongList> data) {
                        showData(data.getData());
                    }
                });
    }

    private void showData(SongList data) {

        //把数据传递到RecycleView
        adapter.setData(data.getSongs());


        RequestBuilder<Bitmap> bitmapRequestBuilder = null;
        if (StringUtils.isBlank(data.getBanner())) {
            //如果头图为空，就用默认图片
            bitmapRequestBuilder = Glide.with(this).asBitmap().load(R.drawable.cd_bg);
        } else {
            bitmapRequestBuilder = Glide.with(this).asBitmap().load(ImageUtil.getImageURI(data.getBanner()));
        }
        bitmapRequestBuilder.into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@NonNull Palette palette) {
                        iv_icon.setImageBitmap(resource);
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        if (swatch != null) {
                            int rgb = swatch.getRgb();
                            toolbar.setBackgroundColor(rgb);                //设置toolbar背景颜色
                            header_container.setBackgroundColor(rgb);       //设置头布局背景颜色
                            //设置状态栏
                            if (android.os.Build.VERSION.SDK_INT >= 21) {   //设置状态栏背景颜色
                                Window window = getWindow();
                                window.setStatusBarColor(rgb);
                                window.setNavigationBarColor(rgb);
                            }
                        }
                    }
                });
            }
        });


        tv_title.setText(data.getTitle());
        tv_nickname.setText(data.getUser().getNickname());
        tv_count.setText(getResources().getString(R.string.music_count, data.getSongs().size()));
        tv_comment_count.setText(String.valueOf(data.getComments_count()));

        //处理收藏歌单按钮的显示和隐藏
        if (data.isCollection()) {//是否收藏
            bt_collection.setText(R.string.cancel_collection_all);//取消歌单
            //已经收藏了，就需要弱化取消收藏按钮，因为我们的本质是想让用户收藏
            bt_collection.setBackgroundDrawable(null);
            bt_collection.setTextColor(getResources().getColor(R.color.text_grey));
        } else {
            bt_collection.setText(R.string.collection_all);//收藏歌单
            bt_collection.setBackgroundResource(R.drawable.selector_button_reverse);//收藏歌单背景selector颜色
          //  bt_collection.setTextColor(getResources().getColorStateList(R.drawable.selector_text_reverse));//收藏歌单文本selector颜色
        }
    }


    @Override
    protected void initToolbar() {
        toolbar.setTitle("歌单");
    }


}
