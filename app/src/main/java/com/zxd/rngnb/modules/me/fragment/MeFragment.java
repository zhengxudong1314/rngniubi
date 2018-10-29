package com.zxd.rngnb.modules.me.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qcloud.image.ImageClient;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.request.NamecardDetectRequest;
import com.zxd.rngnb.R;
import com.zxd.rngnb.base.fragment.BaseFragment;
import com.zxd.rngnb.modules.home.presenter.HomePresenter;
import com.zxd.rngnb.modules.home.view.IHomView;
import com.zxd.rngnb.widget.AppDialog;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建： ZXD
 * 日期 2018/10/23
 * 功能：
 */
public class MeFragment extends BaseFragment<HomePresenter> implements IHomView {
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_list)
    TextView tvList;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void doSomething() {


    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    /**
     * 名片ocr识别操作
     */
    private static void ocrNameCard(ImageClient imageClient, String bucketName, String path) {
        String ret = null;
        // 1. url方式
        File[] namecardImageList = new File[1];
        namecardImageList[0] = new File(path);
        NamecardDetectRequest nameReq = new NamecardDetectRequest(bucketName, namecardImageList, 0);
        try {
            ret = imageClient.namecardDetect(nameReq);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        LogUtils.e("namecard detect ret:" + ret);

    }


    @OnClick(R.id.iv_head)
    public void onViewClicked() {
        AppDialog.openAlbum(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionUtils.permission(PermissionConstants.STORAGE).callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        PictureSelector.create(MeFragment.this).openCamera(PictureMimeType.ofAll())
                                .compress(true).forResult(100);
                    }

                    @Override
                    public void onDenied() {

                    }
                }).request();

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(MeFragment.this).openGallery(PictureMimeType.ofAll())
                        .compress(true).forResult(101);
            }
        }).show();
    }

    private String path;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            path = selectList.get(0).getCompressPath();
            if (TextUtils.isEmpty(path)) {
                path = selectList.get(0).getPath();
            }
            switch (requestCode) {
                case 100:
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final ImageClient imageClient = new ImageClient("1257908792", "AKIDoX4QatQ8ThlfNuhQN8j1yqyv10qUMG6o",
                                    "e7OXVN4b1JTShtyysa6Qf0WuIUK8n9QZ", ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com);
                            ocrNameCard(imageClient, "", path);
                        }
                    }).start();
                    break;
                case 101:

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final ImageClient imageClient = new ImageClient("1257908792", "AKIDoX4QatQ8ThlfNuhQN8j1yqyv10qUMG6o",
                                    "e7OXVN4b1JTShtyysa6Qf0WuIUK8n9QZ", ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com);
                            ocrNameCard(imageClient, "", path);
                        }
                    }).start();
                    break;
            }
        }
    }
}
