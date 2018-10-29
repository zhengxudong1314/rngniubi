package com.zxd.rngnb.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.zxd.pagelayout.PageLayoutManager;
import com.zxd.rngnb.R;
import com.zxd.rngnb.base.presenter.IBasePresenter;
import com.zxd.rngnb.base.view.IBaseView;
import com.zxd.rngnb.widget.AppDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建： ZXD
 * 日期 2018/10/22
 * 功能：
 */
public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView, View.OnClickListener {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        pageLayoutManager = new PageLayoutManager.Builder(getLayoutInflater().inflate(getLayoutId(), null))
                .setErrorLayout(R.layout.view_error)
                .setEmptyLayout(R.layout.view_empty).build();

        //ButterKnife
        unbinder = ButterKnife.bind(this);
        mPresenter = initPresenter();
        mAppDialog = AppDialog.loading(this, null);
        //沉浸式
        BarUtils.setStatusBarLightMode(this, true);

        doSomething();
    }

    /**
     * 提供统一标题方法
     */
    protected void setCustomLayout(int layoutId, String title) {
        //添加统一标题栏
        LinearLayout rootView = (LinearLayout) getLayoutInflater().inflate(R.layout.base_layout, null);
        setContentView(rootView);

        View content = getLayoutInflater().inflate(layoutId, rootView, false);
        rootView.addView(content);
        pageLayoutManager = new PageLayoutManager.Builder(content)
                .setErrorLayout(R.layout.view_error)
                .setEmptyLayout(R.layout.view_empty).build();
        //设置标题需重新绑定
        unbinder = ButterKnife.bind(this);
        initTitleView();
        tvTitle.setText(title);
    }

    private void initTitleView() {
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);
        ivRight = findViewById(R.id.iv_right);
        tvRight = findViewById(R.id.tv_right);
        viewLine = findViewById(R.id.view_line);
        ivBack.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        tvRight.setOnClickListener(this);
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
    protected void onDestroy() {
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
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        mAppDialog.show();
    }

    @Override
    public void hideLoading() {
        mAppDialog.hide();
    }

    /**
     * 点击空白区域隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS
                );
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                supportFinishAfterTransition();
                break;
            case R.id.iv_right:
                break;
            case R.id.tv_right:
                break;
        }
    }
}
