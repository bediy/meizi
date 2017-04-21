package com.example.administrator.mydemos.Request;


import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
public abstract class Builder {

    String url;
    RequestManager.Controller mController;
    Map<String, String> mParams;
    String mTag;
    int mTimeout;

    Builder(RequestManager.Controller mController) {
        this.mController = mController;
    }

    public abstract Builder GET();
    public abstract Builder POST();
    public abstract <T> Builder addCallBack(RequestManager.ResponseCallBack<T> callBack);
    public abstract RequestManager.Controller create();
    public abstract <T> RequestManager.Controller create(Class<T> tClass);

    public Builder URL(String s) {
        url = s;
        return this;
    }

    /**
     * @param ms 毫秒
     * @return Builder$this
     */
    public Builder setTimeout(int ms) {
        mTimeout = ms;
        return this;
    }


    public Builder setParams(Map<String, String> params) {
        mParams = params;
        return this;
    }


    public Builder setTag(String tag) {
        mTag = tag;
        return this;
    }

    protected void reset() {
        url = null;
        mParams = null;
        mTag = null;
    }

    protected void assertNotNull() {
        if (url == null)
            throw new IllegalArgumentException("Url must not null");
    }


}
