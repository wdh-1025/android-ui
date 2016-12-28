package com.ismartlib.ui.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.ismartlib.ui.banner.transformer.AccordionTransformer;
import com.ismartlib.ui.banner.transformer.CubeInTransformer;
import com.ismartlib.ui.banner.transformer.CubeOutTransformer;
import com.ismartlib.ui.banner.transformer.DefaultTransformer;
import com.ismartlib.ui.banner.transformer.DepthPageTransformer;
import com.ismartlib.ui.banner.transformer.FlipHorizontalTransformer;
import com.ismartlib.ui.banner.transformer.FlipVerticalTransformer;
import com.ismartlib.ui.banner.transformer.TabletTransformer;
import com.ismartlib.ui.banner.transformer.ZoomInTransformer;
import com.ismartlib.ui.banner.transformer.ZoomOutSlideTransformer;
import com.ismartlib.ui.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
