package com.zxd.rngnb.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.blankj.utilcode.util.LogUtils;
import com.zxd.rngnb.MyApplication;

import java.io.File;


/**
 * 作者：yzh
 * <p>
 * 创建时间：2017/12/6 11:39
 * <p>
 * 描述：获取一些常用的工具
 * <p>
 * 修订历史：
 */

public class UIUtils {
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到String.xml中的字符串信息
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到String.xml中的字符串数组信息
     */
    public static String[] getStrings(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到Color.xml中的颜色信息
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到应用程序包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 获取屏幕宽高
     *
     * @return
     */
    public static int[] getWindowPx() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int[] px = new int[2];
        px[0] = dm.widthPixels;         // 屏幕宽度（像素）
        px[1] = dm.heightPixels;         // 屏幕高度（像素）
        return px;
    }

    /**
     * 判断是否长屏,超出17：9即为长
     *
     * @return
     */
    public static boolean isLong() {
        int[] windowPx = getWindowPx();
        return windowPx[1] * 9 > windowPx[0] * 17;
    }

    //dp转换成px
    public static float dp2px(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density + 0.5f;
    }

    /**
     * 所有异常的统一上报
     *
     * @param throwable
     */
    public static void catchCrash(Throwable throwable) {
        LogUtils.e("Throwable", throwable.getMessage());
    }

    public static float string2px(String fontSize) {
        String px = fontSize.toLowerCase().replaceAll("px", "");
        return Float.parseFloat(px) * getWindowPx()[0] / 750f;
    }

    /**
     * native尺寸转weex尺寸
     */
    public static float nv2wxPx(float nativePx) {

        return 750f / getWindowPx()[0] * nativePx;

    }

    /**
     * weex尺寸转native尺寸
     */
    public static float wx2nvPx(float weexPx) {

        return getWindowPx()[0] / 750f * weexPx;

    }


    /**
     * 颜色转换
     */
    public static int string2color(String color) {
        return string2color(color, "#FF000000");
    }

    /**
     * 颜色转换
     */
    public static int string2color(String color, String defult) {
        if (TextUtils.isEmpty(color) || "null".equals(color)) {
            color = defult;
        }
        if (color.startsWith("#")) {
            return Color.parseColor(color);
        }

        if (color.length() == 6) {
            return Color.parseColor("#FF" + color);
        }

        if (color.length() == 8) {
            return Color.parseColor("#" + color);
        }
        return Color.parseColor("#FFFF0000");

    }

    public static File getExternalFilesDir(String path) {
        File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = getContext().getExternalFilesDir(null);
            if (file == null || !file.isDirectory()) {
                file = getContext().getCacheDir();
            }
        } else {
            file = getContext().getCacheDir();
        }
        if (TextUtils.isEmpty(path)) {
            return file;
        }
        return new File(file, path);
    }


}