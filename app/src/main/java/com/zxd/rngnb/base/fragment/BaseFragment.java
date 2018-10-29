package com.zxd.rngnb.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxd.pagelayout.PageLayoutManager;
import com.zxd.rngnb.R;
import com.zxd.rngnb.base.view.IBaseView;
import com.zxd.rngnb.widget.AppDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建： ZXD
 * 日期 2018/10/23
 * 功能：
 */
public abstract class BaseFragment<P> extends Fragment implements IBaseView, View.OnClickListener {
    protected ImageView ivBack;
    protected ImageView ivRight;
    protected TextView tvRight;
    protected View viewLine;
    TextView tvTitle;

    private AppDialog mAppDialog;
    private PageLayoutManager pageLayoutManager;

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 在initEventAndData()之前执行
     */
    protected abstract void doSomething();

    /**
     * @return 实例化presenter
     */
    protected abstract P initPresenter();

    protected P mPresenter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        pageLayoutManager = new PageLayoutManager.Builder(view)
                .setErrorLayout(R.layout.view_error)
                .setEmptyLayout(R.layout.view_empty).build();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        mAppDialog = AppDialog.loading(getActivity(), null);
        doSomething();
    }

    /**
     * 提供统一标题方法
     */
    protected void setCustomLayout(int layoutId, String title) {
        //添加统一标题栏
        LinearLayout rootView = (LinearLayout) getLayoutInflater().inflate(R.layout.base_layout, null);
        View view = getLayoutInflater().inflate(layoutId, rootView, false);
        rootView.addView(view);
        pageLayoutManager = new PageLayoutManager.Builder(view)
                .setErrorLayout(R.layout.view_error)
                .setEmptyLayout(R.layout.view_empty).build();
        //设置标题需重新绑定
        unbinder = ButterKnife.bind(this, view);
        tvTitle = view.findViewById(R.id.tv_title);
        ivBack = view.findViewById(R.id.iv_back);
        ivRight = view.findViewById(R.id.iv_right);
        tvRight = view.findViewById(R.id.tv_right);
        viewLine = view.findViewById(R.id.view_line);
        ivBack.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        tvTitle.setText(title);
    }


    /**
     * 设置标题中的右上内容
     *
     * @param title
     */
    public void setCustomTvMore(String title) {
        tvRight.setText(title);
        tvRight.setVisibility(View.VISIBLE);
    }

    public void setCustomTvMore(int resId) {
        ivRight.setImageResource(resId);
        ivRight.setVisibility(View.VISIBLE);
    }
    public void showEmptyView() {
        pageLayoutManager.showEmptyLayout();
    }

    public void showErrorView() {
        pageLayoutManager.showErrorLayout();
    }

    public void showNormalView() {
        pageLayoutManager.showSuccessLayout();
    }
    @Override
    public void onDestroyView() {
        if (mAppDialog != null) {
            mAppDialog.dismiss();
            mAppDialog = null;
        }
        if (mPresenter != null) {
            mPresenter = null;
        }
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().supportFinishAfterTransition();
                break;
            case R.id.iv_right:
                break;
            case R.id.tv_right:
                break;
        }
    }

    @Override
    public void showLoading() {
        mAppDialog.show();
    }

    @Override
    public void hideLoading() {
        mAppDialog.hide();
    }

}
