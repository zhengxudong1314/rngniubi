package com.zxd.rngnb;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zxd.rngnb.base.activity.BaseActivity;
import com.zxd.rngnb.base.presenter.IBasePresenter;
import com.zxd.rngnb.bean.LoginBean;
import com.zxd.rngnb.modules.home.activity.HomeActivity;
import com.zxd.rngnb.utils.ConfigUrl;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phoneNum)
    TextInputEditText etPhoneNum;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void doSomething() {
        setCustomLayout(getLayoutId(),getString(R.string.login));
        boolean isLogin = SPUtils.getInstance().getBoolean("isLogin", false);
        if (isLogin){
            ActivityUtils.startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }
    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                gotoLogin();
                break;
            case R.id.tv_register:
                ActivityUtils.startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void gotoLogin() {
        String phoneNum = etPhoneNum.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        HttpParams httpParams = new HttpParams();
        httpParams.put("username",phoneNum);
        httpParams.put("password",password);
        OkGo.<String>post(ConfigUrl.LOGIN)
                .tag(this)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoginBean loginBean = JSON.parseObject(response.body(), LoginBean.class);
                        if (loginBean != null && loginBean.getErrorCode() == 0) {
                            ActivityUtils.startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            SPUtils.getInstance().put("isLogin", true);
                        } else {
                            ToastUtils.showShort(loginBean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        ToastUtils.showShort("登录失败");
                    }
                });
    }

}
