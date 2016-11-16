package com.ismart.androidui.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ismart.androidui.R;
import com.ismartlib.framework.swipebacklayout.app.SwipeBackActivity;
import com.ismartlib.ui.view.tablayout.TabLayoutSmk;
import com.ismartlib.ui.view.tablayout.demo.FragmentDemo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 标签切换
 */
public class TabLayoutActivity extends SwipeBackActivity implements TabLayoutSmk.CallbackPosition {
    @Bind(R.id.tabLayout)
    TabLayoutSmk tabLayout;
    //标签切换
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private List<Integer> mIconUnselectIds = new ArrayList<>();
    private List<Integer> mIconSelectIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);
        initTab();
    }

    private void initTab() {
        fragments.add(new FragmentDemo());
        fragments.add(new FragmentDemo());
        fragments.add(new FragmentDemo());
        fragments.add(new FragmentDemo());
        mTitles.add("消息");
        mTitles.add("通讯录");
        mTitles.add("应用");
        mTitles.add("我");
        mIconUnselectIds.add(R.drawable.tab_speech_unselect);
        mIconUnselectIds.add(R.drawable.tab_more_unselect);
        mIconUnselectIds.add(R.drawable.tab_home_unselect);
        mIconUnselectIds.add(R.drawable.tab_contact_unselect);
        mIconSelectIds.add(R.drawable.tab_speech_select);
        mIconSelectIds.add(R.drawable.tab_more_select);
        mIconSelectIds.add(R.drawable.tab_home_select);
        mIconSelectIds.add(R.drawable.tab_contact_select);
        tabLayout.init(fragments, mTitles, mIconUnselectIds, mIconSelectIds, getSupportFragmentManager(), this);
        tabLayout.setTextSelectColor(Color.parseColor("#7EACBE"));//设置tabText选中的颜色
        tabLayout.setViewPagerSliding(false);//禁止手动滑动切换
        tabLayout.showMsg(0, 2);  //显示未读消息数量
    }

    @Override
    public void positon(int position) {
        Toast.makeText(this, "切换到了第" + position + "个页面", Toast.LENGTH_SHORT).show();
    }
}
