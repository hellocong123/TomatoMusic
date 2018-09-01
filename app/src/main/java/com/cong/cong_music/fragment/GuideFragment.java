package com.cong.cong_music.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cong.cong_music.Consts;
import com.cong.cong_music.R;
import com.cong.cong_music.util.ImageUtil;
import com.cong.cong_music.util.LogUtil;

import butterknife.BindView;


/**
 * @author Cong
 * @date 2018/8/21
 * @description
 */
public class GuideFragment extends BaseCommonFragment {

    @BindView(R.id.iv)
    ImageView iv;

    private int imageId;

    /**
     * 生成 ViewPager数据集里的Fragment实例，返回一个ViewPager要显示的Fragment界面
     * imageId：其实就是图片的类型，也可以说是一张图片，再把这个图片到到Bundle里面，
     * 显示图片的时候，再获取到这个Bundle里的imageId来显示图片
     *
     */
    public static Fragment newInstance(Integer imageId) {
        Bundle args = new Bundle();//创建一个Bundle，让数据以包的形式存储
        args.putSerializable(Consts.USER_ID, imageId);//把要显示的第几个position，设置为参数放到Fragment
        GuideFragment fragment = new GuideFragment();//根据position创建Fragment
        fragment.setArguments(args);//设置参数
        return fragment;
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, null);
    }

    @Override
    protected void initViews() {
        super.initViews();


    }

    @Override
    protected void initStyles() {
        super.initStyles();
    }

    @Override
    protected void initDatas() {

        //获取到创建Fragment时的参数时，如果是一个异常的id，就把引导界面关闭
        imageId = getArguments().getInt(Consts.USER_ID, -1);

        if (imageId == -1) {
            LogUtil.w("Image id can not be empty!");
            getMainActivity().finish();
            return;
        }

        //创建好的Fragment,都要显示一个图片
        ImageUtil.showLocalImage(getMainActivity(), iv, imageId);
    }

    @Override
    protected void initListener() {

    }
}
