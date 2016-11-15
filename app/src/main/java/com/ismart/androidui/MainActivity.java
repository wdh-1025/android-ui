package com.ismart.androidui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.ismartlib.framework.permissions.Permission;
import com.ismartlib.framework.permissions.PermissionsResult;
import com.ismartlib.framework.permissions.ResultCallBack;
import com.ismartlib.framework.swipebacklayout.app.SwipeBackActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoMultiSelectView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class MainActivity extends SwipeBackActivity {

    @Bind(R.id.image_choose)
    ImageView imageChoose;
    @Bind(R.id.photoMultiSelect)
    PhotoMultiSelectView photoMultiSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        photoMultiSelect();
    }

    /**
     * 图片选择，单选/多选
     */
    @OnClick(R.id.image_choose)
    public void imageChoose() {
        PhotoPickerIntent intent = new PhotoPickerIntent(MainActivity.this);
        intent.setShowCamera(true);
        intent.setTailoring(true);
        intent.setPhotoCount(1);
        intent.setImageSelect(new PhotoPickerActivity.CallbackHead() {
            @Override
            public void selectResults(Bitmap bitmap, List<String> selectedPhotos) {
                //注意:如果选择的是多张图片的话bitmap为null,只返回selectedPhotos
                if (bitmap != null) {
                    imageChoose.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(MainActivity.this, "选择了" + selectedPhotos.size() + "张照片", Toast.LENGTH_SHORT).show();
                }
            }
        });
        startActivity(intent);
    }

    /**
     * 图片多选
     * 注意需要实现onActivityResult
     * view
     */
    private void photoMultiSelect() {
        photoMultiSelect.setMultiSize(8);
        //photoMultiSelect.getPhotos();//获取选择的图片
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
                        } else {
                            //被拒绝
                            Toast.makeText(MainActivity.this, "用户拒绝了你的申请", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //调用view选择多张图片时
        photoMultiSelect.onActivityResult(requestCode, resultCode, data);
    }
}
