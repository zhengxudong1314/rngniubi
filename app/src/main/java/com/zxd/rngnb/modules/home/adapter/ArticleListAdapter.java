package com.zxd.rngnb.modules.home.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zxd.rngnb.R;
import com.zxd.rngnb.bean.ArticleBean;

import java.util.List;

/**
 * 创建： ZXD
 * 日期 2018/10/30
 * 功能：
 */
public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean.DataBean.DatasBean, BaseViewHolder> {
    public ArticleListAdapter(int layoutResId, @Nullable List<ArticleBean.DataBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_author, item.getAuthor().isEmpty() ? "" : item.getAuthor())
                .setText(R.id.tv_title, item.getAuthor().isEmpty() ? "" : item.getTitle())
                .setText(R.id.tv_publishTime, item.getAuthor().isEmpty() ? "" : TimeUtils.millis2String(item.getPublishTime()));
    }
}
