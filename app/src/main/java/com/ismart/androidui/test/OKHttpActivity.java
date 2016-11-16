package com.ismart.androidui.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ismart.androidui.R;
import com.ismartlib.utils.L;
import com.okhttplib.OkHttpUtils;
import com.okhttplib.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class OKHttpActivity extends AppCompatActivity {

    @Bind(R.id.text_content)
    TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_post)
    public void post() {
        textContent.setText("请求中...");
        OkHttpUtils
                .get()
                .url("http://www.baidu.com")
                .addParams("name", "name")
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.i("", "");
                        textContent.setText(e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        textContent.setText(response);
                        L.i("", "");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消当前页面所有未完成请求
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
