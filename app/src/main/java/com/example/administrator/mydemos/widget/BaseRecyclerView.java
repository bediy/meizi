package com.example.administrator.mydemos.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/7/13.
 */
public class BaseRecyclerView extends RecyclerView {

    private static final String TAG = "BaseRecyclerView";
    private OnScrollListener onScrollListener;
    private boolean isInTheBottom = false;
    private int preLoadOffSet = 1;

    public BaseRecyclerView(Context context) {
        super(context);
        init();
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.addOnScrollListener(new OnScrollBottomListener());
    }

    class OnScrollBottomListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            int lastVisibleItemPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
            int itemCount = getAdapter().getItemCount();
            if (dy > 0 && !isInTheBottom && itemCount - preLoadOffSet <= lastVisibleItemPosition) {
                if (onScrollListener != null) {
                    onScrollListener.onReachBottom();
                }
            }

        }
    }

    public void setPreLoadOffSet(int preLoadOffSet) {
        if (preLoadOffSet < 1) {
            throw new IllegalArgumentException("preLoadOffSet must be greater than 0!");
        }
        this.preLoadOffSet = preLoadOffSet;
    }

    public void setIsInTheBottom(boolean status) {
        this.isInTheBottom = status;
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.onScrollListener = listener;
    }

    public interface OnScrollListener {
        void onReachBottom();
    }

}



