package com.zxd.rngnb.base.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;
import com.zxd.rngnb.bean.BannerBean;

/**
 * 创建： ZXD
 * 日期 2018/10/30
 * 功能：自定义Banner加载
 */
public class GlideImageLoader extends ImageLoader {
    /**
     * 设置自己的图片加载框架
     *
     * @param context
     * @param path
     * @param imageView
     */
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        BannerBean.DataBean dataBean = (BannerBean.DataBean) path;
        //加载圆角图片
        RequestOptions mRadiusoptions = RequestOptions.bitmapTransform(new RoundedCorners(30));
        //加载圆形图片
        RequestOptions mCircleOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE);
        GlideApp.with(context).load(dataBean.getImagePath()).into(imageView);
    }

    /**
     * 可以定制自己的ImageView 比如圆角
     *
     * @param context
     * @return
     */
    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }
}
