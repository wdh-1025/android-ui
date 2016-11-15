package me.iwf.photopicker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.adapter.PhotoMultiAdapter;
import me.iwf.photopicker.entity.ImageMulti;
import me.iwf.photopicker.utils.DisplayUtil;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by root on 2016/5/13  14:31.
 */
public class PhotoMultiSelectView extends FrameLayout {
    private RecyclerView recyclerView;
    private ArrayList<ImageMulti> mData = new ArrayList<ImageMulti>();
    private Context mContext;
    private PhotoMultiAdapter photoAdapter;
    private List<String> imagelist = new ArrayList<>();
    private int MultiSize = 9;

    public PhotoMultiSelectView(Context context) {
        this(context, null);
    }

    public PhotoMultiSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.photo_multiselect_view, this);
    }

    private void initView() {
        mData.add(new ImageMulti(null, 2));
        photoAdapter = new PhotoMultiAdapter(mContext, mData, MultiSize);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        //设置recyclerView的高度
        setRecyclerViewHeight(recyclerView);
    }

    /**
     * 获取选择的图片
     *
     * @return
     */
    public ArrayList<ImageMulti> getPhotos() {
        return this.mData;
    }

    /**
     * 设置最多张数
     *
     * @param MultiSize
     */
    public void setMultiSize(int MultiSize) {
        this.MultiSize = MultiSize;
        initView();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<String> photos = null;
        if (resultCode == -1 && requestCode == 1) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            mData.clear();
            //加入用户选择的照片
            if (photos != null) {
                for (int i = 0; i < photos.size(); i++) {
                    mData.add(new ImageMulti(photos.get(i), 1));
                }
                //删除后还是会加载这里，妹的
                if (!photoAdapter.isDelete) {
                    for (int i = 0; i < imagelist.size(); i++) {
                        if (!judge(imagelist.get(i))) {
                            mData.add(new ImageMulti(imagelist.get(i), 1));
                        }
                    }
                } else {
                    //重新保存删除后的照片
                    imagelist.clear();
                    for (int i = 0; i < mData.size(); i++) {
                        imagelist.add(mData.get(i).getImagePath());
                    }
                    photoAdapter.isDelete = false;
                }
                mData.add(new ImageMulti(null, 2));
            }
            //保存选择的照片
            imagelist.clear();
            for (int i = 0; i < mData.size() - 1; i++) {
                imagelist.add(mData.get(i).getImagePath());
            }
            //为0时添加1项拍照项
            if (photoAdapter.getItemCount() == 0) {
                imagelist.clear();
                mData.add(new ImageMulti(null, 2));
            }

            setRecyclerViewHeight(recyclerView);
            photoAdapter.notifyDataSetChanged();
        }
    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<String> photos = null;
        if (resultCode == -1 && requestCode == 1) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            mData.clear();
            //加入用户选择的照片
            if (photos != null) {
                for (int i = 0; i < photos.size(); i++) {
                    mData.add(new ImageMulti(photos.get(i), 1));
                }
                //删除后还是会加载这里，妹的
                if (!photoAdapter.isDelete) {
                    for (int i = 0; i < imagelist.size(); i++) {
                        if (!judge(imagelist.get(i))) {
                            mData.add(new ImageMulti(imagelist.get(i), 1));
                        }
                    }
                } else {
                    //重新保存删除后的照片
                    imagelist.clear();
                    for (int i = 0; i < mData.size(); i++) {
                        imagelist.add(mData.get(i).getImagePath());
                    }
                    photoAdapter.isDelete = false;
                }
                mData.add(new ImageMulti(null, 2));
            }
            //保存选择的照片
            imagelist.clear();
            for (int i = 0; i < mData.size() - 1; i++) {
                imagelist.add(mData.get(i).getImagePath());
            }
            //为0时添加1项拍照项
            if (photoAdapter.getItemCount() == 0) {
                imagelist.clear();
                mData.add(new ImageMulti(null, 2));
            }

            setRecyclerViewHeight(recyclerView);
            photoAdapter.notifyDataSetChanged();
        }
    }*/

    private boolean judge(String str) {
        for (int i = 0; i < mData.size(); i++) {
            if (str.equals(mData.get(i).getImagePath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 设置recyclerview高度
     *
     * @param recyclerview
     */
    private void setRecyclerViewHeight(RecyclerView recyclerview) {
        int pxHeight = DisplayUtil.dip2px(mContext, 80);  //recyclerview的高度
        int margin = DisplayUtil.dip2px(mContext, 4);  //图片item的上下边距
        int px = pxHeight;
        int height = 0;
        if (photoAdapter.getItemCount() < 4 || photoAdapter.getItemCount() == 4) {
            height = px + margin;
        } else if (photoAdapter.getItemCount() > 4 && photoAdapter.getItemCount() < 9) {
            height = (px * 2) + (margin * 2);
        } else if (photoAdapter.getItemCount() > 8) {
            height = (px * 3) + (margin * 3);
        }
        ViewGroup.LayoutParams params = recyclerview.getLayoutParams();
        params.height = height;
        recyclerview.setLayoutParams(params);
    }
}
