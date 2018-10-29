package com.zxd.rngnb.widget;

/**
 * 创建： ZXD
 * 日期 2018/10/18
 * 功能：防止重复点击
 */

public class AntiShake {
    private static LimitQueue<NoDoubleClickListener> queue = new LimitQueue<>(20);

    public static boolean isFastClick(Object o) {
        String flag;
        if (o == null) {
            flag = Thread.currentThread().getStackTrace()[2].getMethodName();
        } else {
            flag = o.toString();
        }
        for (NoDoubleClickListener util : queue.getArrayList()) {
            if (util.getMethodName().equals(flag)) {
                return util.isFastClick();
            }
        }
        NoDoubleClickListener clickUtil = new NoDoubleClickListener(flag);
        queue.offer(clickUtil);
        return clickUtil.isFastClick();
    }

}
