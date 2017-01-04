package com.example.administrator.mydemos.api;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.administrator.mydemos.Request.RequestManager;
import com.example.administrator.mydemos.model.AllData;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2015/9/6.
 */
public class GankApi {

    //默认请求的数据类型(福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all)
    public static final String DATA_TYPE = "福利";
    //请求个数
    public static final int DATA_NUM = 5;

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
                .append(DATA_TYPE + "/")
                .append(String.valueOf(DATA_NUM) + "/")
                .append(String.valueOf(page))
                .toString();

        return new Net(Request.Method.GET, url, type, netCallBack, null);
    }

    public static String getAllDataUrl(int page) {
        return new StringBuilder()
                .append("http://gank.io/api/data/")
                .append(DATA_TYPE + "/")
                .append(String.valueOf(DATA_NUM) + "/")
                .append(String.valueOf(page))
                .toString();
    }

    public void getdata() {
        String url = new StringBuilder()
                .append("http://gank.io/api/data/")
                .append(DATA_TYPE + "/")
                .append(String.valueOf(DATA_NUM) + "/")
                .append(String.valueOf(1))
                .toString();

        RequestManager.ResponseCallBack<AllData> callBack = new RequestManager.ResponseCallBack<AllData>() {
            @Override
            public void onResponse(AllData response) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }
}
