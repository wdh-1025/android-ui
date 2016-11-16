package com.ismart.androidui.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ismart.androidui.R;
import com.ismartlib.framework.swipebacklayout.app.SwipeBackActivity;
import com.ismartlib.ui.dialog.DialogOnClickListener;
import com.ismartlib.ui.dialog.DialogOnItemClickListener;
import com.ismartlib.ui.dialog.MDAlertDialog;
import com.ismartlib.ui.dialog.MDEditDialog;
import com.ismartlib.ui.dialog.MDSelectionDialog;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MD风格提示框
 */
public class MDDialogActivity extends SwipeBackActivity {
    private MDAlertDialog dialog1;
    private MDSelectionDialog dialog2;
    private MDEditDialog dialog3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mddialog);
        ButterKnife.bind(this);

        dialog1 = new MDAlertDialog.Builder(MDDialogActivity.this)
                .setHeight(0.21f)  //屏幕高度*0.21
                .setWidth(0.7f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("温馨提示")
                .setTitleTextColor(R.color.black_light)
                .setContentText("确定发送文件？")
                .setContentTextColor(R.color.black_light)
                .setLeftButtonText("不发送")
                .setLeftButtonTextColor(R.color.gray)
                .setRightButtonText("发送")
                .setRightButtonTextColor(R.color.gray)
                .setTitleTextSize(16)
                .setContentTextSize(14)
                .setButtonTextSize(14)
                .setOnclickListener(new DialogOnClickListener() {
                    @Override
                    public void clickLeftButton(View view) {
                        dialog1.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialog1.dismiss();
                    }
                })
                .build();

        final ArrayList<String> s = new ArrayList<>();
        s.add("标为未读");
        s.add("置顶聊天");
        s.add("删除聊天");
        dialog2 = new MDSelectionDialog.Builder(MDDialogActivity.this)
                .setCanceledOnTouchOutside(true)
                .setItemTextColor(R.color.black_light)
                .setItemHeight(50)
                .setItemWidth(0.8f)  //屏幕宽度*0.8
                .setItemTextSize(15)
                .setCanceledOnTouchOutside(true)
                .setOnItemListener(new DialogOnItemClickListener() {
                    @Override
                    public void onItemClick(Button button, int position) {
                        Toast.makeText(MDDialogActivity.this, s.get(position), Toast.LENGTH_SHORT).show();
                        dialog2.dismiss();
                    }
                })
                .build();
        dialog2.setDataList(s);


        dialog3 = new MDEditDialog.Builder(MDDialogActivity.this)
                .setTitleVisible(true)
                .setTitleText("设置密码")
                .setTitleTextSize(20)
                .setTitleTextColor(R.color.black_light)
                .setContentText("password")
                .setContentTextSize(18)
                .setMaxLength(7)
                .setHintText("最少8位")
                .setMaxLines(1)
                .setContentTextColor(R.color.colorPrimary)
                .setButtonTextSize(14)
                .setLeftButtonTextColor(R.color.colorPrimary)
                .setLeftButtonText("取消")
                .setRightButtonTextColor(R.color.colorPrimary)
                .setRightButtonText("确定")
                .setLineColor(R.color.colorPrimary)
                .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                    @Override
                    public void clickLeftButton(View view, String editText) {
                        dialog3.dismiss();
                        Toast.makeText(MDDialogActivity.this, editText.trim(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void clickRightButton(View view, String editText) {
                        Toast.makeText(MDDialogActivity.this, editText.trim(), Toast.LENGTH_SHORT).show();
                        dialog3.dismiss();
                    }
                })
                .setMinHeight(0.3f)
                .setWidth(0.8f)
                .build();
    }


    @OnClick(R.id.btn_md_alert)
    public void MDAlert() {
        dialog1.show();
    }


    @OnClick(R.id.btn_md_select)
    public void MDSelect() {
        dialog2.show();
    }


    @OnClick(R.id.btn_md_edit)
    public void MDEdit() {
        dialog3.show();
    }
}
