package com.zxd.rngnb.widget;

import java.util.Calendar;

/**
 * 创建： ZXD
 * 日期 2018/10/18
 * 功能：
 */

public class NoDoubleClickListener {
    private String methodName;
    private static final int CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    public NoDoubleClickListener(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean isFastClick() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        } else {
            return true;
        }
    }

}
