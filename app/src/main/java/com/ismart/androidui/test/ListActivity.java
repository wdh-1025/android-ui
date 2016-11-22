package com.ismart.androidui.test;

import android.os.Handler;

import com.ismart.androidui.adapter.ATListAdapter;
import com.ismartlib.ui.base.adapter.BaseListAdapter;
import com.ismartlib.ui.base.ui.BaseListActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseListActivity<String> {


    @Override
    protected void OnViewCreated() {
        showLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideEmptyLayout();
            }
        }, 1000);
    }

    @Override
    public void requestData(boolean needRefresh) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> mData = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    mData.add("我ia沙发哈撒" + i + "自行车自行车");
                }
                onDataLoaded(mData);
            }
        }, 1000);
    }

    @Override
    protected BaseListAdapter<String> getListAdapter() {
        return new ATListAdapter(this);
    }


}
