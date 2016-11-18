package com.ismartlib.ui.view.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ismartlib.R;
import com.ismartlib.utils.DisplayUtil;

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
 * Created by  Administrator  on 2016/11/16.
 * Email:924686754@qq.com
 */
public class LeftRightText extends FrameLayout {
    private Context mContext;
    /**
     * 左右图标
     */
    private int leftIcon, rightIcon;
    /**
     * 左右文本
     */
    private String leftText, rightText;
    /**
     * 左右图标大小
     */
    private float leftIconSize, rightIconSize;
    /**
     * 左右文字颜色
     */
    private int leftTextColor, rightTextColor;
    /**
     * 左右上下边距
     */
    private float leftRightPadding, topBottomPadding;

    private ImageView ImageLeftIcon, ImageRightIcon;
    private TextView TextLeftText, TextRightText;
    private LinearLayout layout_item;

    public LeftRightText(Context context) {
        this(context, null);
    }

    public LeftRightText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.item_leftright_text, this);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = attrs == null ? null : getContext().obtainStyledAttributes(attrs, R.styleable.ItemLeftRightText);
        if (ta != null) {
            leftIcon = ta.getResourceId(R.styleable.ItemLeftRightText_leftIcon, 0);
            leftIconSize = ta.getDimension(R.styleable.ItemLeftRightText_leftIconSize, 17);
            leftText = ta.getString(R.styleable.ItemLeftRightText_leftText);
            leftTextColor = ta.getColor(R.styleable.ItemLeftRightText_leftTextColor, getResources().getColor(R.color.black));
            rightText = ta.getString(R.styleable.ItemLeftRightText_rightText);
            rightIcon = ta.getResourceId(R.styleable.ItemLeftRightText_rightIcon, 0);
            rightTextColor = ta.getColor(R.styleable.ItemLeftRightText_rightTextColor, getResources().getColor(R.color.gray));
            rightIconSize = ta.getDimension(R.styleable.ItemLeftRightText_rightIconSize, 17);
            leftRightPadding = ta.getDimension(R.styleable.ItemLeftRightText_leftRightPadding, 12);
            topBottomPadding = ta.getDimension(R.styleable.ItemLeftRightText_topBottomPadding, 12);
            initView();
        }
    }

    private void initView() {
        ImageLeftIcon = (ImageView) findViewById(R.id.image_leftIcon);
        ImageRightIcon = (ImageView) findViewById(R.id.image_rightIcon);
        TextLeftText = (TextView) findViewById(R.id.text_leftText);
        TextRightText = (TextView) findViewById(R.id.text_rightText);
        layout_item = (LinearLayout) findViewById(R.id.layout_item);
        //设置图片
        if (leftIcon != 0) {
            ImageLeftIcon.setVisibility(View.VISIBLE);
            ImageLeftIcon.setImageResource(leftIcon);
        }
        //设置大小
        DisplayUtil.setViewWidthHeight(ImageLeftIcon, (int) leftIconSize, (int) leftIconSize);
        //设置文字
        leftText = leftText == null ? "" : leftText;
        TextLeftText.setText(leftText);
        //设置字体颜色
        TextLeftText.setTextColor(leftTextColor);
        //设置右边文字
        rightText = rightText == null ? "" : rightText;
        TextRightText.setText(rightText);
        //设置右边图标
        if (rightIcon != 0) {
            ImageRightIcon.setVisibility(View.VISIBLE);
            ImageRightIcon.setImageResource(rightIcon);
        }
        //设置右边文字颜色
        TextRightText.setTextColor(rightTextColor);
        //设置图标大小
        DisplayUtil.setViewWidthHeight(ImageRightIcon, (int) rightIconSize, (int) rightIconSize);
        layout_item.setPadding((int) leftRightPadding, (int) topBottomPadding, (int) leftRightPadding, (int) topBottomPadding);
    }
}
