package me.iwf.photopicker.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * dp、sp 转换为 px 的工具类
 */
public class DisplayUtil {

    private static float density;
    private static float scaledDensity;

    private static void setDensity(Context context) {
        if (density <= 0 || scaledDensity <= 0) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            density = dm.density;
            scaledDensity = dm.scaledDensity;
        }
    }

    /**
     * 获取屏幕的密度
     *
     * @return 屏幕的密度
     */
    public static float getScreenDensity(Context context) {

        setDensity(context);

        return density;
    }

    public static float getScaledDensity(Context context) {

        setDensity(context);

        return scaledDensity;
    }

    /**
     * 获取屏幕的宽度
     *
     * @return 屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        density = dm.density;
        scaledDensity = dm.scaledDensity;
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    /**
     * 获取屏幕的高度
     *
     * @return 屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        density = dm.density;
        scaledDensity = dm.scaledDensity;
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 将dip转换为px
     *
     * @param dip
     * @return dip转换为px
     */
    public static int dip2px(Context context, int dip) {
        if (density <= 0) {
            setDensity(context);
        }
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 将dip转换为px
     *
     * @param dip
     * @return dip转换为px
     */
    public static float dip2px(Context context, float dip) {
        if (density <= 0) {
            setDensity(context);
        }
        return dip * density + 0.5f * (dip >= 0 ? 1 : -1);

    }

    /**
     * 将px转换为dip
     *
     * @param px
     * @return px转换为dip
     */
    public static int px2dip(Context context, int px) {
        if (density <= 0) {

            setDensity(context);
        }
        return (int) (px / density + 0.5f * (px >= 0 ? 1 : -1));
    }

    /**
     * sp 转换为 px
     *
     * @param sp
     * @return sp 转换为 px
     */
    public static int sp2px(Context context, int sp) {
        if (scaledDensity <= 0) {

            setDensity(context);
        }
        return (int) (sp * scaledDensity + 0.5f * (sp >= 0 ? 1 : -1));
    }

    /**
     * sp 转换为 px
     *
     * @param sp
     * @return sp 转换为 px
     */
    public static float sp2px(Context context, float sp) {
        if (scaledDensity <= 0) {

            setDensity(context);
        }
        return sp * scaledDensity + 0.5f * (sp >= 0 ? 1 : -1);
    }

    /**
     * px 转换为 sp
     *
     * @param px
     * @return px 转换为 sp
     */
    public static int px2sp(Context context, int px) {
        if (scaledDensity <= 0) {

            setDensity(context);
        }
        return (int) (px / scaledDensity + 0.5f * (px >= 0 ? 1 : -1));
    }

    /***************************************************/
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     * @paramscale （DisplayMetrics类中属性density）
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     * @paramfontScale （DisplayMetrics类中属性scaledDensity）
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

}