package com.ismart.androidui.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ismart.androidui.R;
import com.ismart.androidui.adapter.BannerLoaderImage;
import com.ismartlib.ui.banner.Banner;
import com.ismartlib.ui.banner.BannerConfig;
import com.ismartlib.ui.banner.listener.OnBannerClickListener;
import com.ismartlib.ui.banner.transformer.AccordionTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BannerActivity extends AppCompatActivity implements OnBannerClickListener {
    @Bind(R.id.banner1)
    Banner banner1;
    @Bind(R.id.banner2)
    Banner banner2;
    @Bind(R.id.banner3)
    Banner banner3;
    @Bind(R.id.banner4)
    Banner banner4;
    @Bind(R.id.banner5)
    Banner banner5;
    @Bind(R.id.banner6)
    Banner banner6;
    @Bind(R.id.banner7)
    Banner banner7;
    @Bind(R.id.banner8)
    Banner banner8;
    @Bind(R.id.banner9)
    Banner banner9;
    @Bind(R.id.banner10)
    Banner banner10;
    @Bind(R.id.banner11)
    Banner banner11;
    private List<String> imgUrl = new ArrayList<>();
    private List<String> titles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        imgUrl.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        imgUrl.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        imgUrl.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        imgUrl.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        titles.add("51巅峰钜惠");
        titles.add("十大星级品牌联盟，全场2折起");
        titles.add("生命不是要超越别人，而是要超越自己。");
        titles.add("嗨购5折不要停");
        start();
    }

    private void start() {
        //简单使用
        banner1.setImages(imgUrl)
                .setImageLoader(new BannerLoaderImage())
                .setOnBannerClickListener(this)
                .start();
        //带动画，所有动画在transformer下
        banner2.setImages(imgUrl)
                .setImageLoader(new BannerLoaderImage())
                .setOnBannerClickListener(this)
                .start();
        banner2.setBannerAnimation(AccordionTransformer.class);
        //样式一
        banner3.setImages(imgUrl)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.NOT_INDICATOR)
                .setImageLoader(new BannerLoaderImage())
                .start();
        //样式二
        banner4.setImages(imgUrl)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new BannerLoaderImage())
                .start();
        //样式三
        banner5.setImages(imgUrl)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .setImageLoader(new BannerLoaderImage())
                .start();
        //样式四
        banner6.setImages(imgUrl)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImageLoader(new BannerLoaderImage())
                .start();
        //样式五
        banner7.setImages(imgUrl)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
                .setImageLoader(new BannerLoaderImage())
                .start();
        //样式六
        banner8.setImages(imgUrl)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new BannerLoaderImage())
                .start();

        //下面是自定义属性
        banner9.setImages(imgUrl)
                .setImageLoader(new BannerLoaderImage())
                .start();

        banner10.setImages(imgUrl)
                .setImageLoader(new BannerLoaderImage())
                .start();

        banner11.setImages(imgUrl)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new BannerLoaderImage())
                .start();

    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(this, "点击了" + position, Toast.LENGTH_SHORT).show();
    }
}
