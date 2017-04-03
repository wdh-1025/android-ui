package com.okhttplib.builder;


import com.okhttplib.OkHttpUtils;
import com.okhttplib.request.OtherRequest;
import com.okhttplib.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id, cache).build();
    }
}
