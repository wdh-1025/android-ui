package com.ismartlib.ui.view.tablayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.ismartlib.R;
import com.ismartlib.ui.view.tablayout.entity.TabEntity;
import com.ismartlib.ui.view.tablayout.listener.CustomTabEntity;
import com.ismartlib.ui.view.tablayout.listener.OnTabSelectListener;
import com.ismartlib.ui.view.tablayout.view.CommonTabLayout;
import com.ismartlib.ui.view.tablayout.widget.MyViewPager;

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
 * Created by root on 2016/5/23  10:19.
 * Email:924686754@qq.com
 */
public class TabLayoutSmk extends FrameLayout {
    private MyViewPager viewpager;
    private CommonTabLayout tl_2;
    private Context mContext;
    //Fragment
    private List<Fragment> mFragments = null;
    //tab文字描述
    private List<String> mTitles = null;
    //tab选项
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    //未选图片
    private List<Integer> mIconUnselectIds = null;
    //已选图片
    private List<Integer> mIconSelectIds = null;
    private Random mRandom = new Random();
    private CallbackPosition mCallbackPosition;

    public TabLayoutSmk(Context context) {
        this(context, null);
    }

    public TabLayoutSmk(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.tablayout_view_tab_smk, this);
        initView();
    }

    private void initView() {
        viewpager = (MyViewPager) findViewById(R.id.viewpager);
        tl_2 = (CommonTabLayout) findViewById(R.id.tl_2);
    }


    /**
     * @param mFragments       fragement
     * @param mTitles          //tab文字描述
     * @param mIconUnselectIds //未选图标
     * @param mIconSelectIds   //选择图标
     * @param fm               //FragmentManager
     */
    public void init(List<Fragment> mFragments, List<String> mTitles,
                     List<Integer> mIconUnselectIds, List<Integer> mIconSelectIds, FragmentManager fm, CallbackPosition callbackPosition) {
        this.mFragments = mFragments;
        this.mTitles = mTitles;
        this.mIconUnselectIds = mIconUnselectIds;
        this.mIconSelectIds = mIconSelectIds;
        this.mCallbackPosition = callbackPosition;
        this.show(fm);
    }

    private void show(FragmentManager fm) {
        viewpager.setAdapter(new ViewPagerAdapter(mFragments, mTitles, fm));
        for (int i = 0; i < mFragments.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), mIconSelectIds.get(i), mIconUnselectIds.get(i)));
        }
        tl_2.setTabData(mTabEntities);
        tl_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
                mCallbackPosition.positon(position);
            }

            @Override
            public void onTabReselect(int position) {
               /* if (position == 0) {
                    tl_2.showMsg(0, mRandom.nextInt(100) + 1);
                }*/
            }
        });
        //监听滑动事件
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tl_2.setCurrentTab(position);
                mCallbackPosition.positon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //初始化页面
        viewpager.setCurrentItem(0);

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

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void showMsg(int position, int num) {
        tl_2.showMsg(position, num);
    }

    public interface CallbackPosition {
        public void positon(int position);
    }

    /**
     * 设置ViewPager是否可以左右滑动
     */
    public void setViewPagerSliding(boolean is) {
        viewpager.setSliding(is);
    }
}
