package com.ismart.androidui.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.ismart.androidui.R;
import com.ismartlib.ui.base.ui.BaseLayoutActivity;
import com.ismartlib.ui.base.utils.ListSettings;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoMultiSelectView;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * 图片选择
 */
public class ImageChooseActivity extends BaseLayoutActivity {
    @Bind(R.id.image_choose)
    ImageView imageChoose;
    @Bind(R.id.photoMultiSelect)
    PhotoMultiSelectView photoMultiSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMyContentView(R.layout.activity_image_choose);
        ButterKnife.bind(this);
        photoMultiSelect();
        showNoData();
        setReloadCallBack(new ReloadCallBack() {
            @Override
            public void Reload() {
                hideEmptyLayout();
            }
        });

        ListSettings settings = new ListSettings();
        settings.setRlNodataMaskBackColor(2);
    }

    /**
     * 图片选择，单选/多选
     */
    @OnClick(R.id.image_choose)
    public void imageChoose() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
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
                    Toast.makeText(ImageChooseActivity.this, "选择了" + selectedPhotos.size() + "张照片", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //调用view选择多张图片时
        photoMultiSelect.onActivityResult(requestCode, resultCode, data);
    }
}
