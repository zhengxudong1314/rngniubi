package com.zxd.rngnb.modules.home.fragment;

import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zxd.rngnb.R;
import com.zxd.rngnb.base.fragment.BaseFragment;
import com.zxd.rngnb.base.glide.GlideImageLoader;
import com.zxd.rngnb.bean.ArticleBean;
import com.zxd.rngnb.bean.BannerBean;
import com.zxd.rngnb.modules.home.adapter.ArticleListAdapter;
import com.zxd.rngnb.modules.home.presenter.HomePresenter;
import com.zxd.rngnb.modules.home.view.IHomView;
import com.zxd.rngnb.utils.ConfigUrl;

import java.util.List;

import butterknife.BindView;

/**
 * 创建： ZXD
 * 日期 2018/10/23
 * 功能：
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements IHomView {

    @BindView(R.id.normal_view)
    ConstraintLayout normalView;
    Banner mBanner;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void doSomething() {
        LinearLayout mHeaderGroup = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_banner, null);
        mBanner = mHeaderGroup.findViewById(R.id.banner);
        mHeaderGroup.removeView(mBanner);

        smartRefreshLayout.setEnableLoadMore(false);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //获取banner数据
        OkGo.<String>post(ConfigUrl.BANNER).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                BannerBean bannerBean = JSON.parseObject(response.body(), BannerBean.class);
                if (bannerBean.getErrorCode() == 0 && bannerBean.getData() != null) {
                    initBanner(bannerBean.getData());
                }
            }
        });
        //获取文章列表数据
        OkGo.<String>get(ConfigUrl.ARTICLE).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                ArticleBean articleBean = JSON.parseObject(response.body(), ArticleBean.class);
                if (articleBean.getErrorCode() == 0 && articleBean.getData() != null) {
                    initArticleList(articleBean.getData().getDatas());
                }
            }
        });
    }

    private void initArticleList(List<ArticleBean.DataBean.DatasBean> dataBeans) {
        ArticleListAdapter articleListAdapter = new ArticleListAdapter(R.layout.view_cardview,dataBeans);
        articleListAdapter.addHeaderView(mBanner);
        rv.setAdapter(articleListAdapter);
    }

    private void initBanner(List<BannerBean.DataBean> bannerDatas) {
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(bannerDatas);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //mBanner.setBannerTitles(mBannerTitleList);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.NOT_INDICATOR);
        //图片切换监听
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mBanner.start();
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }


}
