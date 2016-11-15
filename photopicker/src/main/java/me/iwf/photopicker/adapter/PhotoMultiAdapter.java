package me.iwf.photopicker.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.R;
import me.iwf.photopicker.entity.ImageMulti;
import me.iwf.photopicker.utils.PhotoPickerIntent;

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
 * Created by root on 2016/5/13  14:36.
 */
public class PhotoMultiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ImageMulti> photoPaths = new ArrayList<ImageMulti>();
    private ArrayList<String> image = new ArrayList<String>();
    private LayoutInflater inflater;
    private Context mContext;
    private static int type1 = 1;
    private static int type2 = 2;
    public static boolean isDelete = false;
    private int MultiSize = 0;

    public PhotoMultiAdapter(Context mContext, ArrayList<ImageMulti> photoPaths, int MultiSize) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        this.MultiSize = MultiSize;
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        RecyclerView.ViewHolder holer = null;
        switch (viewType) {
            case 1:
                itemView = inflater.inflate(R.layout.item_image, parent, false);
                holer = new PhotoViewHolder(itemView);
                break;
            case 2:
                itemView = inflater.inflate(R.layout.item_photos, parent, false);
                holer = new photoHolder(itemView);
                break;

        }
        return holer;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                final int pos = position;
                PhotoViewHolder mPhoto = (PhotoViewHolder) holder;
                Uri uri = Uri.fromFile(new File(photoPaths.get(position).getImagePath()));
                Glide.with(mContext)
                        .load(uri)
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.ic_photo_black_48dp)
                        .error(R.drawable.ic_broken_image_black_48dp)
                        .into(mPhoto.ivPhoto);
                final ImageView iamge = mPhoto.ivPhoto;
                mPhoto.ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, PhotoPagerActivity.class);
                        intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, pos);
                        image.clear();
                        for (int i = 0; i < photoPaths.size() - 1; i++) {
                            image.add(photoPaths.get(i).getImagePath());
                        }
                        intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, image);
                        if (mContext != null) {
                            Activity activity = (Activity) mContext;
                            activity.startActivityForResult(intent, 1);
                        }
                        isDelete = true;
                    }
                });

                break;
            case 2:
                photoHolder mPhotos = (photoHolder) holder;
                if (photoPaths.size() > MultiSize) {
                    mPhotos.photo.setVisibility(View.GONE);
                } else {
                    mPhotos.photo.setVisibility(View.VISIBLE);
                }
                mPhotos.photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
                        //intent.setPhotoCount(10 - photoPaths.size());
                        intent.setPhotoCount((MultiSize + 1) - photoPaths.size());
                        Activity activity = (Activity) mContext;
                        activity.startActivityForResult(intent, 1);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (photoPaths.get(position).getType() == 1) {
            return type1;
        } else {
            return type2;
        }
    }

    @Override
    public int getItemCount() {
        return photoPaths.size();
    }


    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
        }
    }

    /**
     * 拍照按钮
     */
    public class photoHolder extends RecyclerView.ViewHolder {
        private ImageView photo;

        public photoHolder(View itemView) {
            super(itemView);
            this.photo = (ImageView) itemView.findViewById(R.id.photo);
        }
    }

    public int getViewHeight(View v) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);
        int height = v.getMeasuredHeight();
        int width = v.getMeasuredWidth();
        return height;
    }
}

