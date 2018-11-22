package com.cong.cong_music.activity;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cong.cong_music.Consts;
import com.cong.cong_music.R;
import com.cong.cong_music.activity.base.BaseToolBarActivity;
import com.cong.cong_music.adapter.BaseRecyclerViewAdapter;
import com.cong.cong_music.adapter.CommentAdapter;
import com.cong.cong_music.adapter.TestAdapter;
import com.cong.cong_music.api.ApiService;
import com.cong.cong_music.api.RetrofitUtils;
import com.cong.cong_music.bean.Comment;
import com.cong.cong_music.bean.ListResponse;
import com.cong.cong_music.bean.SongList;
import com.cong.cong_music.bean.response.DetailResponse;
import com.cong.cong_music.fragment.CommentMoreDialogFragment;
import com.cong.cong_music.reactivex.HttpListener;
import com.cong.cong_music.util.LogUtil;
import com.cong.cong_music.util.ToastUtil;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommentListActivity extends BaseToolBarActivity implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener, CommentAdapter.OnCommentAdapter {

    private String listId;
    private int style;
    private HashMap<String, String> querys;
    private LRecyclerView rv;
    private CommentAdapter adapter;
    private LRecyclerViewAdapter adapterWrapper;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    EditText et_content;
    private ListResponse.Meta pageMate;
    private Button bt_send;
    private String parentId;//回复的id

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_comment_list);

        rv = findViewById(R.id.rv);
        et_content = findViewById(R.id.et_content);
        bt_send = findViewById(R.id.bt_send);
        super.initViews();

    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("评论");
    }


    @Override
    protected void initDatas() {

        listId = getIntent().getStringExtra(Consts.LIST_ID);
        style = getIntent().getIntExtra(Consts.STYLE, 0);


        adapter = new CommentAdapter(getActivity());
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        rv.setAdapter(mLRecyclerViewAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //添加分割线
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        rv.addItemDecoration(decoration);
        //条目点击事件
        adapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                Object data = adapter.getData(position);
                if (data instanceof Comment) {
                    showCommentMoreDialog((Comment) data);
                    ToastUtil.showSortToast(getActivity(), "我是评论：" + position);
                }
            }
        });


        //去获取网络数据
        fetchData();
    }

    //点击评论 显示 Dialog
    private void showCommentMoreDialog(final Comment data) {
        CommentMoreDialogFragment dialogFragment = new CommentMoreDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                processOnClick(which, data);
            }
        });
    }

    public void processOnClick(int which, Comment data) {

        switch (which) {
            case 0:
                //回复评论
               parentId = data.getId();
                et_content.setHint(getResources().getString(R.string.reply_comment, data.getUser().getNickname()));

                //TODO 分享评论
                LogUtil.d("回复评论");
                break;
            case 1:
                //TODO 分享评论
                LogUtil.d("分享评论");
                break;
            case 2:
                //TODO 复制评论
                LogUtil.d("复制评论");
                break;
            case 3:
                //TODO 举报评论
                LogUtil.d("举报评论");
                break;
        }
    }

    private void fetchData() {

        final ArrayList<Object> objects = new ArrayList<>();
        objects.add("精彩评论");

        final HashMap<String, String> querys = getQuerys();//请求参数id

        querys.put(Consts.ORDER, String.valueOf(Consts.ORDER_HOT));

        if (StringUtils.isNotBlank(listId)) {
            querys.put(Consts.LIST_ID, listId);
        }


        RetrofitUtils.getInstance().comments(querys)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpListener<ListResponse<Comment>>(getActivity()) {
                    @Override
                    public void onSucceeded(ListResponse<Comment> data) {

                        objects.addAll(data.getData());
                        adapter.setData(objects);           //第一次是setData
                        adapter.addData("最新评论");

                        loadMore();
                    }
                });

    }

    private void loadMore() {

        final ArrayList<Object> objects = new ArrayList<>();

        final HashMap<String, String> querys = getQuerys();
        if (StringUtils.isNotBlank(listId)) {
            querys.put(Consts.LIST_ID, listId);
        }
        //在加载更多的时候，要在当前页那里+1
        //String.valueOf:是要把页数改成String类型
        //第一次加载的时候，pageMate肯定是为null的，所以这里面返回第一页，第二次不为Null了就+1
        querys.put(Consts.PAGE, String.valueOf(ListResponse.Meta.nextPage(pageMate)));

        //最新评论
        RetrofitUtils.getInstance().comments(querys)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpListener<ListResponse<Comment>>(getActivity()) {
                    @Override
                    public void onSucceeded(ListResponse<Comment> data) {

                        objects.addAll(data.getData());
                        adapter.addData(objects);                               //第二次是addData
                        pageMate = data.getMeta();
                        rv.refreshComplete(Consts.DEFAULT_PAGE_SIZE);                     //刷新完成
                    }
                });

    }

    public HashMap<String, String> getQuerys() {
        final HashMap<String, String> querys = new HashMap<>();
        if (StringUtils.isNotBlank(listId)) {
            querys.put(Consts.LIST_ID, listId);
        }

        return querys;
    }


    @Override
    protected void initListener() {
        rv.setOnRefreshListener(this);
        rv.setOnLoadMoreListener(this);
        bt_send.setOnClickListener(this);
        adapter.setOnCommentAdapter(this);
    }


    //使用LRecycleView设置下拉刷新
    @Override
    public void onRefresh() {
        pageMate.setCurrent_page(0);                                //下拉刷新的时候要把当前页重新设置为0
        fetchData();                                                //重新请求一次网络
    }

    //使用LRecycleView设置上拉加载更多
    @Override
    public void onLoadMore() {
        //当前页数小于总页数的时候才可以继续刷新，否则就没有加载更多了
        if (pageMate == null || pageMate.getCurrent_page() < pageMate.getTotal_pages()) {
            loadMore();
        } else {
            rv.setNoMore(true);//没有加载更多了
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:                                  //点击发送评论
                sendComment();
                break;
        }
    }



    //发送评论
    private void sendComment() {

        //判断用户有没有输入内容
        String content = et_content.getText().toString().trim();
        if (StringUtils.isEmpty(content)) {
            ToastUtil.showSortToast(getActivity(), getString(R.string.enter_comment_content));
            return;
        }

        Comment comment = new Comment();    //把Comment作为请求对象发送网络请求
        comment.setParent_id(parentId);     //点击回复评论时获取的评论ID
        comment.setContent(content);        //回复内容
        comment.setSheet_id(listId);        //歌单Id，发布评论用户
        comment.setStyle(style);            //区分评论来源，0:视频,10:单曲,20:专辑,30:歌单

        RetrofitUtils.getInstance().createComment(comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpListener<DetailResponse<Comment>>(getActivity()) {
                    @Override
                    public void onSucceeded(DetailResponse<Comment> data) {

                        ToastUtil.showSortToast(getActivity(), getString(R.string.comment_create_susscess));//评论发布成功
                        onRefresh();

                        ////重新设置最热评论，然后在拉去第一页评论
                        ////当然也可以手动将发送的评论插入到adapter中
                        ////拉去第一页
//                        pageMate.setCurrent_page(1);
//                        loadMore();


                        et_content.setText("");     //清空输入框
                        clearReplyComment();        //parentId制空，清空回复的评论
                        //关闭键盘
//                        KeyboardUtil.hideKeyboard(getActivity());
                    }
                });

    }

    private void clearReplyComment() {
        parentId = null;
        et_content.setHint(R.string.hint_comment);
    }

    //点赞的点击事件
    @Override
    public void onLikeClick(final Comment comment) {

        //评论是否点赞
        if (comment.isLiked()) {

            //已经点赞
            RetrofitUtils.getInstance().unlike(comment.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpListener<DetailResponse<Comment>>(getActivity()) {
                        @Override
                        public void onSucceeded(DetailResponse<Comment> data) {

                            //可以调用接口，也可以在本地加减
                            comment.setLikes_count(comment.getLikes_count() - 1);
                            comment.setLike_id(null);
                            adapter.notifyDataSetChanged();
                        }
                    });
        } else {

            RetrofitUtils.getInstance().like(comment.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpListener<DetailResponse<Comment>>(getActivity()) {
                        @Override
                        public void onSucceeded(DetailResponse<Comment> data) {

                            comment.setLikes_count(comment.getLikes_count() + 1);
                            //comment.isLiked()判断有没有点赞就是根据这里有没有值来判断的
                            //这里可以随便设置一个值，有值就证明已经点过赞了
                            comment.setLike_id("1");
                            adapter.notifyDataSetChanged();
                        }
                    });
        }
    }
}
