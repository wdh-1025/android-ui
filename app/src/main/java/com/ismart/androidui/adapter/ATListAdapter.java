package com.ismart.androidui.adapter;

import android.content.Context;

import com.ismart.androidui.R;
import com.ismartlib.ui.base.adapter.BaseListAdapter;
import com.ismartlib.ui.base.adapter.BaseViewHolder;

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
 * Created by  Administrator  on 2016/11/21.
 * Email:924686754@qq.com
 */
public class ATListAdapter extends BaseListAdapter<String> {


    public ATListAdapter(Context context) {
        super(context, R.layout.item_list);
    }

    @Override
    protected void onBindContentViewData(BaseViewHolder helper, String item, int position) {
        helper.setText(R.id.tv, item);
    }

    @Override
    protected void onBindHeadViewData(BaseViewHolder helper, int position) {

    }
}
