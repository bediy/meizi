package com.example.administrator.mydemos.api;

import com.android.volley.Request;
import com.example.administrator.mydemos.model.AllData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2015/9/6.
 */
public class GankApi {

    //默认请求的数据类型(福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all)
    public static final String DATA_MEIZI = "福利/";
    public static final String DATA_ALL = "all/";
    //请求个数
    public static final int DATA_NUM = 10;

    public static final String HEAD = "http://gank.io/api/data/";

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * @param page  第几页
     * @param netCallBack
     * @return
     */
    public static Net<AllData> getAllData(int page, Net.NetCallBack<AllData> netCallBack) {

        Type type = new TypeToken<AllData>() {
        }.getType();

        String url = new StringBuilder()
                .append("http://gank.io/api/data/")
                .append(DATA_MEIZI + "/")
                .append(String.valueOf(DATA_NUM) + "/")
                .append(String.valueOf(page))
                .toString();

        return new Net(Request.Method.GET, url, type, netCallBack, null);
    }

    public static String getMeiziDataUrl(int page) {
        return new StringBuilder()
                .append(HEAD)
                .append(DATA_MEIZI)
                .append(DATA_NUM + "/")
                .append(page)
                .toString();
    }

    public static String getAllDataUrl(int page) {
        return new StringBuilder()
                .append(HEAD)
                .append(DATA_ALL)
                .append(DATA_NUM + "/")
                .append(page)
                .toString();
    }

}
