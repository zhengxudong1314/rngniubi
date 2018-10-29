package com.zxd.rngnb.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zxd.rngnb.R;

/**
 * 创建： ZXD
 * 日期 2018/10/23
 * 功能：
 */
public class AppDialog extends Dialog {

    private static Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private AppDialog(Context context, @LayoutRes int layoutRes, @StyleRes int styleRes, int width, int height) {
        super(context, styleRes);
        // set content
        setContentView(layoutRes);

        window = getWindow();
        if (window != null) {

            WindowManager.LayoutParams params = window.getAttributes();
            if (width > 0) {
                params.width = width;
            } else {
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            if (height > 0) {
                params.height = height;
            } else {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            params.gravity = Gravity.CENTER;
            params.y = -context.getResources().getDimensionPixelSize(R.dimen.dp_50);

            window.setAttributes(params);
        }
    }

    /**
     * 等待dialog
     */
    public static AppDialog loading(Context context, String text) {
        final AppDialog dialog = new AppDialog(context, R.layout.dialog_loading, R.style.AppDialogRadius, 0, 0);
        dialog.setCanceledOnTouchOutside(false);
        if (!TextUtils.isEmpty(text)) {
            TextView tv = dialog.findViewById(R.id.tv_loading);
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }

        return dialog;
    }

    /**
     * 双按钮（确认类型）dialog
     */
    public static AppDialog confirm(Context context, String msg, String left, String right,
                                    final View.OnClickListener leftListener, final View.OnClickListener rightListener) {
        final AppDialog dialog = new AppDialog(context, R.layout.dialog_confirm, R.style.AppDialogRadius, 0, 0);
        dialog.setCanceledOnTouchOutside(false);
        TextView tvMsg = dialog.findViewById(R.id.tv_message);
        if (tvMsg != null) {
            if (!TextUtils.isEmpty(msg))
                tvMsg.setText(msg);
        }

        Button cancel = dialog.findViewById(R.id.bt_cancel);
        if (cancel != null) {
            if (!TextUtils.isEmpty(left))
                cancel.setText(left);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (leftListener != null) {
                        leftListener.onClick(v);
                    }
                    dialog.dismiss();
                }
            });
        }

        Button confirm = dialog.findViewById(R.id.bt_confirm);
        if (confirm != null) {
            if (!TextUtils.isEmpty(right))
                confirm.setText(right);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rightListener != null)
                        rightListener.onClick(v);
                    dialog.dismiss();
                }
            });
        }

        return dialog;
    }

    /**
     * 相机、相册 dialog
     */
    public static AppDialog openAlbum(Context context, final View.OnClickListener cameraListener, final View.OnClickListener albumListener) {
        final AppDialog dialog = new AppDialog(context, R.layout.dialog_album, R.style.AppDialogBottom, 0, 0);
        dialog.setCanceledOnTouchOutside(true);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.y = -context.getResources().getDimensionPixelSize(R.dimen.dp_50);
        window.setAttributes(params);

        TextView tvCamera = dialog.findViewById(R.id.tv_camera);
        TextView tvAlbum = dialog.findViewById(R.id.tv_album);
        TextView tvCancle = dialog.findViewById(R.id.tv_cancle);
        tvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cameraListener != null)
                    cameraListener.onClick(view);
                dialog.dismiss();
            }
        });
        tvAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (albumListener != null)
                    albumListener.onClick(view);
                dialog.dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
