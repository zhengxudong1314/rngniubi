package com.zxd.rngnb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zxd.rngnb.bean.LoginBean;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkGo.<String>post("http://www.wanandroid.com/user/login")
                .tag(this)
                .params("username", "13161962287")
                .params("password", "12345678")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoginBean loginBean = JSON.parseObject(response.body(), LoginBean.class);
                        LogUtils.e(response.body());
                        if (loginBean.getErrorCode()==0){
                            ToastUtils.showShort("登录成功");
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        //取消网络请求
        OkGo.cancelAll(OkGo.getInstance().getOkHttpClient());
        super.onDestroy();
    }
}
