package com.zxd.rngnb.base.presenter;


import com.lzy.okgo.OkGo;
import com.zxd.rngnb.base.view.IBaseView;


/**
 * 创建： ZXD
 * 日期 2018/8/23
 * 功能：
 */

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    protected V mView;

}
