package com.ismartlib.ui.view.tablayout.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

public class FragmentChangeManager {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    /**
     * Fragment切换数组
     */
    private ArrayList<Fragment> mFragments;
    /**
     * 当前选中的Tab
     */
    private int mCurrentTab = 0;

    public FragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<Fragment> fragments) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
        initFragments();
    }

    /**
     * 初始化fragments
     */
    private void initFragments() {
       /* for (Fragment fragment : mFragments) {
            mFragmentManager.beginTransaction().add(mContainerViewId, fragment).hide(fragment).commit();
        }*/
        mFragmentManager.beginTransaction().add(mContainerViewId, mFragments.get(0)).commit();
        setFragments(0);
    }


    private void addTab(int index) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment fragment = mFragments.get(index);
        if (!fragment.isAdded()) {
            ft.hide(mFragments.get(mCurrentTab));
            ft.add(mContainerViewId, fragment).commit();
        }
    }

    /**
     * 界面切换控制
     *
     * @paramindex
     */
    public void setFragments(int index) {
        if(mCurrentTab!=index){
            addTab(index);
        }
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            if (i == index) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        mCurrentTab = index;
    }
    /*public void checkedChanged(int index) {

        for (int i = 0; i < mFragments.size(); i++) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            getCurrentFragment().onPause(); // 暂停当前tab
            if (fragment.isAdded()) {
                fragment.onResume(); // 启动目标tab的onResume()
            } else {
                mFragmentManager.beginTransaction().add(mContainerViewId, fragment);
            }
            showTab(i); // 显示目标tab
            ft.commit();
        }
    }

    */

    /**
     * 界面切换控制
     *//*
    public void setFragments(int index) {
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            if (i == index) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        mCurrentTab = index;
    }*/
    public int getCurrentTab() {
        return mCurrentTab;
    }

    public Fragment getCurrentFragment() {
        return mFragments.get(mCurrentTab);
    }
}