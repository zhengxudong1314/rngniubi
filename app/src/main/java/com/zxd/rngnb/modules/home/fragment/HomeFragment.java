package com.zxd.rngnb.modules.home.fragment;

import android.support.constraint.ConstraintLayout;

import com.zxd.rngnb.R;
import com.zxd.rngnb.base.fragment.BaseFragment;
import com.zxd.rngnb.modules.home.presenter.HomePresenter;
import com.zxd.rngnb.modules.home.view.IHomView;

import butterknife.BindView;

/**
 * 创建： ZXD
 * 日期 2018/10/23
 * 功能：
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements IHomView {

    @BindView(R.id.normal_view)
    ConstraintLayout normalView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void doSomething() {
        //first commit
        //sencond commit
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }



}
