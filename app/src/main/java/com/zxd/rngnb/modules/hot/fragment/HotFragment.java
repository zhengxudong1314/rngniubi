package com.zxd.rngnb.modules.hot.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.zxd.rngnb.R;
import com.zxd.rngnb.base.fragment.BaseFragment;
import com.zxd.rngnb.modules.home.presenter.HomePresenter;
import com.zxd.rngnb.modules.home.view.IHomView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建： ZXD
 * 日期 2018/10/23
 * 功能：
 */
public class HotFragment extends BaseFragment<HomePresenter> implements IHomView {
    RecyclerView rv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void doSomething() {
        rv = (RecyclerView) getActivity().findViewById(R.id.rv);
        final List<String> list = new ArrayList<>();
        list.add("大流氓苏仨");
        list.add("冯提莫的小粉丝");
        list.add("八连杀");
        list.add("大话西游");
        list.add("卢本伟牛逼");
        list.add("RNG更牛逼");
        list.add("老司机开车了滴滴滴");
        list.add("大流氓苏仨");
        list.add("老司机开车了滴滴滴老司机开车了滴滴滴老司机开车了滴滴滴老司机开车了滴滴滴");
        list.add("冯提莫的小粉丝");
        list.add("八连杀");
        list.add("大话西游");
        list.add("帅");
        list.add("卢本伟牛逼");
        list.add("RNG更牛逼");
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getActivity());
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        rv.setLayoutManager(flexboxLayoutManager);
        BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_textview, list) {

            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_text, item);
            }
        };
        rv.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort(list.get(position));
            }
        });
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

}
