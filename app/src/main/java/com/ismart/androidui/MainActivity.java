package com.ismart.androidui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ismart.androidui.test.BannerActivity;
import com.ismart.androidui.test.ImageChooseActivity;
import com.ismart.androidui.test.ListActivity;
import com.ismart.androidui.test.MDDialogActivity;
import com.ismart.androidui.test.OKHttpActivity;
import com.ismart.androidui.test.TabLayoutActivity;
import com.ismart.androidui.test.ViewActivity;
import com.ismartlib.framework.permissions.Permission;
import com.ismartlib.framework.permissions.PermissionsResult;
import com.ismartlib.framework.permissions.ResultCallBack;
import com.ismartlib.framework.swipebacklayout.app.SwipeBackActivity;
import com.ismartlib.ui.photoview.PhotoViewIntent;
import com.ismartlib.ui.photoview.utils.ImageAttribute;
import com.ismartlib.ui.photoview.utils.ImageDetailFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends SwipeBackActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * 图片选择
     */
    @OnClick(R.id.btn_image_choose)
    public void btn_image_choose() {
        startActivity(new Intent(this, ImageChooseActivity.class));
    }


    /**
     * 权限请求,多个权限后面追加
     */
    @OnClick(R.id.btn_permission)
    public void permissionRequest() {
        Permission.getInstance(this)
                .requestPermission(Manifest.permission.CAMERA)
                .results(new ResultCallBack() {
                    @Override
                    public void result(PermissionsResult result) {
                        if (result.isGranted()) {
                            //有权限
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                            Toast.makeText(MainActivity.this, "有权限啦", Toast.LENGTH_SHORT).show();
                            Permission.getInstance(MainActivity.this).unresults();
                        } else {
                            //被拒绝
                            Toast.makeText(MainActivity.this, "用户拒绝了你的申请", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 页面标签切换
     */
    @OnClick(R.id.btn_tablayout)
    public void tabLayout() {
        startActivity(new Intent(this, TabLayoutActivity.class));
    }

    /**
     * MD风格dialog
     */
    @OnClick(R.id.btn_dialog)
    public void MDDialog() {
        startActivity(new Intent(this, MDDialogActivity.class));
    }

    @OnClick(R.id.btn_okhttp)
    public void OKHttp() {
        startActivity(new Intent(this, OKHttpActivity.class));
    }

    @OnClick(R.id.btn_view)
    public void View() {
        startActivity(new Intent(this, ViewActivity.class));
    }

    @OnClick(R.id.btn_list)
    public void List() {
        startActivity(new Intent(this, ListActivity.class));
    }

    @OnClick(R.id.btn_banner)
    public void Banner() {
        startActivity(new Intent(this, BannerActivity.class));
    }

    @OnClick(R.id.btn_photo_view)
    public void photoView() {
        //可自己决定用什么加载图片框架去加载图片
        //只要适当调用fragment.onLoadingStarted();更新状态即可
        ArrayList<String> urls = new ArrayList<>();
        urls.add("http://img07.tooopen.com/images/20170206/tooopen_sy_198052642226.jpg");
        urls.add("http://img06.tooopen.com/images/20170120/tooopen_sy_197334647747.jpg");
        PhotoViewIntent photoViewIntent = new PhotoViewIntent(this);
        photoViewIntent.setImageUrls(urls)
                .getImageAttribute(new ImageAttribute() {
                    @Override
                    public void ImageAttribute(ImageView imageView, String imageUrl, final ImageDetailFragment fragment) {
                        ImageLoader.getInstance().displayImage(imageUrl, imageView, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                                fragment.onLoadingStarted();
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                String message = null;
                                switch (failReason.getType()) {
                                    case IO_ERROR:
                                        message = "下载错误";
                                        break;
                                    case DECODING_ERROR:
                                        message = "图片无法显示";
                                        break;
                                    case NETWORK_DENIED:
                                        message = "网络有问题，无法下载";
                                        break;
                                    case OUT_OF_MEMORY:
                                        message = "图片太大无法显示";
                                        break;
                                    case UNKNOWN:
                                        message = "未知的错误";
                                        break;
                                }
                                fragment.onLoadingFailed(message);
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                fragment.onLoadingComplete();
                            }
                        });
                    }
                });
        startActivity(photoViewIntent);
    }


}
