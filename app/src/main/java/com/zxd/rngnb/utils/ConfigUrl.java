package com.zxd.rngnb.utils;

import com.zxd.rngnb.MyApplication;

/**
 * 创建： ZXD
 * 日期 2018/10/22
 * 功能：
 */
public class ConfigUrl {
    public static String LOGIN, REGISTER, BANNER, FRIEND, ARTICLE;
    public static String baseUrl = "";
    public static final String BASE_ON_LINE = "http://www.wanandroid.com/";//正式
    public static String BASE_TEST = "http://www.wanandroid.com/";//测试

    //给url赋值
    public static void resetUrl() {
        if (MyApplication.isApkInDebug()) {
            baseUrl = BASE_TEST;
        } else {
            baseUrl = BASE_ON_LINE;
        }

        /**
         * 登陆
         * http://www.wanandroid.com/user/login
         *
         * @param username user name
         * @param password password
         * @return 登陆数据
         */
        LOGIN = baseUrl + "user/login";

        /**
         * 注册
         * http://www.wanandroid.com/user/register
         *
         * @param username user name
         * @param password password
         * @param repassword re password
         * @return 注册数据
         */
        REGISTER = baseUrl + "user/register";

        /**
         * 广告栏
         * http://www.wanandroid.com/banner/json
         *
         * @return 广告栏数据
         */
        BANNER = baseUrl + "banner/json";

        /**
         * 常用网站
         * http://www.wanandroid.com/friend/json
         *
         * @return 常用网站数据
         */
        FRIEND = baseUrl + "friend/json";

        /**
         * 获取feed文章列表
         *
         * @param num 页数 article/list/{num}/json
         * @return feed文章列表数据
         */
        ARTICLE = baseUrl + "article/list/1/json";
    }

    //进行url赋值
    static {
        resetUrl();
    }
}
