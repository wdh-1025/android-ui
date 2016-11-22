package com.ismart.androidui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

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



}
