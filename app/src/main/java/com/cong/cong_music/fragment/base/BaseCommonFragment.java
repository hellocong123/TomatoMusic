package com.cong.cong_music.fragment.base;



import com.cong.cong_music.util.OrmUtil;
import com.cong.cong_music.util.SharedPreferencesUtil;

/**
 * @author Cong
 * @date 2018/8/21
 * @description
 */
public abstract class BaseCommonFragment extends BaseFragment{
    protected SharedPreferencesUtil sp;
    protected OrmUtil orm;
    @Override
    protected void initViews() {
        super.initViews();
        sp = SharedPreferencesUtil.getInstance(getActivity().getApplicationContext());
        orm = OrmUtil.getInstance(getActivity().getApplicationContext());
    }
}
