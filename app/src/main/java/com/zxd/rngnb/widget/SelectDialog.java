package com.zxd.rngnb.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxd.rngnb.R;
import com.zxd.rngnb.bean.SelectBean;

import java.util.List;

/**
 * 创建： ZXD
 * 日期 2018/10/15
 * 功能：
 */

public class SelectDialog extends Dialog implements View.OnClickListener {

    private TextView tvDialogTitle;
    private RecyclerView rvDialog;
    private TextView tvDialogCancle;
    private TextView tvDialogSure;
    private List<SelectBean> selectBeans;
    private SelectClickListener mSelectClickListener;
    private SelectAdapter selectAdapter;
    private boolean mSingleSelect;
    private String mTitleName;

    public SelectDialog(@NonNull Context context, List<SelectBean> selectBeans, SelectClickListener selectClickListener) {
        super(context);
        this.selectBeans = selectBeans;
        mSelectClickListener = selectClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_select_dialog);
        tvDialogTitle = findViewById(R.id.tv_dialogTitle);
        rvDialog = findViewById(R.id.rv_dialog);
        tvDialogCancle = findViewById(R.id.tv_dialogCancle);
        tvDialogSure = findViewById(R.id.tv_dialogSure);
        tvDialogCancle.setOnClickListener(this);
        tvDialogSure.setOnClickListener(this);
        if (!TextUtils.isEmpty(mTitleName)) {
            tvDialogTitle.setText(mTitleName);
        }
        rvDialog.setLayoutManager(new LinearLayoutManager(getContext()));
        selectAdapter = new SelectAdapter(selectBeans);
        rvDialog.setAdapter(selectAdapter);
        selectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageView ivCb = view.findViewById(R.id.iv_cb);
                if (mSingleSelect) {
                    boolean select = selectBeans.get(position).isSelect;
                    select = !select;
                    for (SelectBean dataBean : selectBeans) {
                        dataBean.isSelect = false;
                    }

                    selectBeans.get(position).isSelect = select;
                    ivCb.setSelected(select);
                    selectAdapter.notifyDataSetChanged();
                } else {
                    boolean select = selectBeans.get(position).isSelect;
                    select = !select;
                    selectBeans.get(position).isSelect = select;
                    ivCb.setSelected(select);
                }
            }
        });
    }

    public void setTitle(String title) {
        this.mTitleName = title;
    }

    public void setSingleSelect(boolean singleSelect) {
        mSingleSelect = singleSelect;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialogCancle:
                dismiss();
                if (mSelectClickListener != null) {
                    mSelectClickListener.clickCancle(this, selectBeans);
                }
                break;
            case R.id.tv_dialogSure:
                dismiss();
                if (mSelectClickListener != null) {
                    mSelectClickListener.clickSure(this, selectBeans);
                }
                break;
        }
    }

    public interface SelectClickListener {

        void clickCancle(Dialog dialog, Object data);

        void clickSure(Dialog dialog, Object data);
    }

    public class SelectAdapter extends BaseQuickAdapter<SelectBean, BaseViewHolder> {

        public SelectAdapter(@Nullable List<SelectBean> data) {
            super(R.layout.item_select_dialog, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SelectBean item) {
            helper.setText(R.id.tv_content, TextUtils.isEmpty(item.selectName) ? "" : item.selectName);
            ImageView ivCb = helper.getView(R.id.iv_cb);
            ivCb.setSelected(item.isSelect);
        }
    }
}
