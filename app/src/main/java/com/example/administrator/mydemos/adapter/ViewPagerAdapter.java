package com.example.administrator.mydemos.adapter;

import android.animation.ObjectAnimator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.administrator.mydemos.R;
import com.example.administrator.mydemos.model.Results;
import com.example.administrator.mydemos.widget.SwipePhotoView;

import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2016/8/3.
 */
public class ViewPagerAdapter extends PagerAdapter implements View.OnTouchListener {

    private static final String TAG = ViewPagerAdapter.class.getSimpleName();

    private List<Results> results;
    private AppCompatActivity context;
    private int mLastY;
    private int mOffSetY;
    private OnItemListener onItemListener;


    public ViewPagerAdapter(AppCompatActivity context, List<Results> list) {
        this.results = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final SwipePhotoView photoView = new SwipePhotoView(container.getContext());
        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);
        photoView.setTransitionName(results.get(position).getUrl());
        photoView.setAttacher(attacher);
        photoView.setOnTouchListener(this);
        container.addView(photoView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        Glide
                .with(container.getContext())
                .load(results.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(photoView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        attacher.update();
                        if (onItemListener != null) {
                            onItemListener.startPostponed(position, photoView);
                        }
                    }
                });
        return photoView;
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (onItemListener != null) {
            onItemListener.onPrimaryItem(container, position, object);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        SwipePhotoView photoView = (SwipePhotoView) object;
        photoView.getAttacher().cleanup();
        container.removeView(photoView);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        SwipePhotoView photoView = (SwipePhotoView) v;
        if (photoView.getAttacher().getScale() == PhotoView.DEFAULT_MIN_SCALE) {
            int y = (int) event.getRawY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mOffSetY = 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int deltaY = y - mLastY;
                    int translationY = (int) (v.getTranslationY() + deltaY);
                    mOffSetY = mOffSetY + deltaY;
                    if (Math.abs(mOffSetY) > 50)
                        v.setTranslationY(translationY);
                    break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs(mOffSetY) > 300) {
                        return shouldFinish(v);
                    } else {
                        int currTranslationY = (int) v.getTranslationY();
                        ObjectAnimator
                                .ofFloat(v, "translationY", currTranslationY, 0)
                                .setDuration(context.getResources().getInteger(R.integer.transitions_duration))
                                .start();
                    }
                    break;
                default:
                    break;
            }
            mLastY = y;
        }
        return photoView.getAttacher().onTouch(v, event);
    }

    private boolean shouldFinish(View view) {
        if (onItemListener == null) {
            return false;
        }
        if (view.getTranslationY() > 0)
            onItemListener.onFinish(view.getHeight(), view);
        else
            onItemListener.onFinish(-view.getHeight(), view);
        return true;
    }

    public void setOnItemListener(OnItemListener listener) {
        onItemListener = listener;
    }

    public interface OnItemListener {
        void onPrimaryItem(ViewGroup container, int position, Object object);
        void startPostponed(int position, View view);
        void onFinish(int translationY, View view);
    }
}
