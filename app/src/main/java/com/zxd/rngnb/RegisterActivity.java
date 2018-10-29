package com.zxd.rngnb;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zxd.rngnb.base.activity.BaseActivity;
import com.zxd.rngnb.base.presenter.IBasePresenter;
import com.zxd.rngnb.bean.LoginBean;
import com.zxd.rngnb.utils.ConfigUrl;
import com.zxd.rngnb.widget.AppDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_phoneNum)
    TextInputEditText etPhoneNum;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.et_repassword)
    TextInputEditText etRepassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private AppDialog appDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void doSomething() {
        setCustomLayout(R.layout.activity_register, getString(R.string.register));
        /*appDialog = AppDialog.confirm(this, "内容。。。。。。。",
                "取消", "确定",
                null, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });*/
        appDialog = AppDialog.openAlbum(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("相机");
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("相册");
            }
        });
    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }


    @OnClick(R.id.tv_register)
    public void onViewClicked() {
        appDialog.show();
        String phoneNum = etPhoneNum.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String repassword = etRepassword.getText().toString().trim();
        HttpParams httpParams = new HttpParams();
        httpParams.put("username", phoneNum);
        httpParams.put("password", password);
        httpParams.put("repassword", repassword);
        OkGo.<String>post(ConfigUrl.REGISTER)
                .tag(this)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoginBean loginBean = JSON.parseObject(response.body(), LoginBean.class);
                        if (loginBean != null && loginBean.getErrorCode() == 0) {
                            ToastUtils.showShort("注册成功");
                        } else {
                            ToastUtils.showShort(loginBean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ToastUtils.showShort("注册失败");
                    }
                });
    }

}
