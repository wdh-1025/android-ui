package com.ismartlib.ui.view.tablayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.ismartlib.R;
import com.ismartlib.ui.view.tablayout.entity.TabEntity;
import com.ismartlib.ui.view.tablayout.listener.CustomTabEntity;
import com.ismartlib.ui.view.tablayout.listener.OnTabSelectListener;
import com.ismartlib.ui.view.tablayout.view.CommonTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
 * 　　　　┃　　　┃永无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by Administrator on 2016/8/4.
 * Email:924686754@qq.com
 */

public class TabLayoutFTSmk extends FrameLayout {
    private CommonTabLayout tl_2;
    private Context mContext;
    //Fragment
    private ArrayList<Fragment> mFragments = null;
    //tab文字描述
    private List<String> mTitles = null;
    //tab选项
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    //未选图片
    private List<Integer> mIconUnselectIds = null;
    //已选图片
    private List<Integer> mIconSelectIds = null;
    private Random mRandom = new Random();
    private TabLayoutSmk.CallbackPosition mCallbackPosition;

    public TabLayoutFTSmk(Context context) {
        this(context, null);
    }

    public TabLayoutFTSmk(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.tablayout_view_tab_ft_smk, this);
        initView();
    }

    private void initView() {
        tl_2 = (CommonTabLayout) findViewById(R.id.tl_2);
    }

    /**
     * @param mFragments       fragement
     * @param mTitles          //tab文字描述
     * @param mIconUnselectIds //未选图标
     * @param mIconSelectIds   //选择图标
     * @param fm               //FragmentManager
     */
    public void init(ArrayList<Fragment> mFragments, List<String> mTitles,
                     List<Integer> mIconUnselectIds, List<Integer> mIconSelectIds, FragmentManager fm, TabLayoutSmk.CallbackPosition callbackPosition) {
        this.mFragments = mFragments;
        this.mTitles = mTitles;
        this.mIconUnselectIds = mIconUnselectIds;
        this.mIconSelectIds = mIconSelectIds;
        this.mCallbackPosition = callbackPosition;
        this.show(fm);
    }

    private void show(FragmentManager fm) {
        for (int i = 0; i < mFragments.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), mIconSelectIds.get(i), mIconUnselectIds.get(i)));
        }
        tl_2.setTabData(mTabEntities, (FragmentActivity) mContext, R.id.fl_change, mFragments);
        tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mCallbackPosition.positon(position);
            }

            @Override
            public void onTabReselect(int position) {
                //第二次选择tab
            }
        });
        //默认只能显示第一页，因为显示后面的话必须先加载前面的，所以放弃
        tl_2.setCurrentTab(0);

    }

    /**
     * 设置Tab Text选中的颜色
     *
     * @param color
     */
    public void setTextSelectColor(int color) {
        tl_2.setTextSelectColor(color);
    }

    /**
     * 设置Tab Text默认的颜色
     *
     * @param color
     */
    public void setTextUnselectColor(int color) {
        tl_2.setTextUnselectColor(color);
    }

    public void showMsg(int position, int num) {
        tl_2.showMsg(position, num);
    }
}
