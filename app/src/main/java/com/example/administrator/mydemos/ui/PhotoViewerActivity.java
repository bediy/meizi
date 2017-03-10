package com.example.administrator.mydemos.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.android.volley.VolleyError;
import com.example.administrator.mydemos.R;
import com.example.administrator.mydemos.adapter.ViewPagerAdapter;
import com.example.administrator.mydemos.api.GankApi;
import com.example.administrator.mydemos.api.Net;
import com.example.administrator.mydemos.model.AllData;
import com.example.administrator.mydemos.model.Results;
import com.example.administrator.mydemos.widget.SwipePhotoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewerActivity extends AppCompatActivity implements ViewPagerAdapter.OnItemListener, Net.NetCallBack<AllData> {

    private static final String TAG = PhotoViewerActivity.class.getSimpleName();
    public static final String EXTRA_LIST = "list";
    public static final String EXTRA_START_POSITION = "startPosition";
    public static final String EXTRA_CURRENT_POSITION = "currentPosition";
    public static final String EXTRA_FIRST_VISIBLE_POSITION = "firstPosition";
    public static final String EXTRA_LAST_VISIBLE_POSITION = "lastPosition";
    public static final String EXTRA_START_TRANSITION_NAME = "startTransitionName";
    public static final String EXTRA_CURR_PAGE = "currentPage";

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ViewPagerAdapter adapter;
    private ArrayList<Results> list;
    private SwipePhotoView sharedElement;
    private int startPosition;
    private int currPosition;

    private int firstPosition;
    private int lastPosition;
    private String startTransitionName;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postponeEnterTransition();
        setContentView(R.layout.activity_photo_viewer);
        setEnterSharedElementCallback(new OnSharedElementCallback());

        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        list = getIntent().getParcelableArrayListExtra(EXTRA_LIST);
        startPosition = getIntent().getIntExtra(EXTRA_START_POSITION, 0);
        firstPosition = getIntent().getIntExtra(EXTRA_FIRST_VISIBLE_POSITION, 0);
        lastPosition = getIntent().getIntExtra(EXTRA_LAST_VISIBLE_POSITION, 0);
        startTransitionName = getIntent().getStringExtra(EXTRA_START_TRANSITION_NAME);
        page = getIntent().getIntExtra(EXTRA_CURR_PAGE, 0);

        viewPager.setAdapter(adapter = new ViewPagerAdapter(this, list));
        viewPager.setCurrentItem(startPosition);
        viewPager.addOnPageChangeListener(new PageChangeListener());
        adapter.setOnItemListener(this);

        setupWindowAnimations();

    }

    private void setupWindowAnimations() {/*
        Slide slide = new Slide();
        slide.setDuration(500);*/
//        getWindow().setEnterTransition(null);
       /* Fade fade = new Fade();
        fade.setDuration(200);
        getWindow().setReturnTransition(fade);
        getWindow().setExitTransition(fade);*/
//        Fade fadeIn = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade_out);
//        getWindow().setReturnTransition(fadeIn);
    }

    @Override
    public void onPrimaryItem(ViewGroup container, int position, Object object) {
        currPosition = position;
        sharedElement = (SwipePhotoView) object;
    }

    @Override
    public void startPostponed(int position, View view) {
        if (position == startPosition)
            scheduleStartPostponedTransition(view);
    }

    private void scheduleStartPostponedTransition(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }

    private void postRequest() {
        Net<AllData> dataNet = GankApi.getAllData(page, this);
        dataNet.addToQueue(TAG);
    }

    @Override
    public void onResponse(AllData response) {
        if (!response.getError()) {
            if (response.getResults().size() != 0) {
                list.addAll(response.getResults());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    class PageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            currPosition = position;
            if (position > list.size() - 2) {
                page++;
                postRequest();
            }
        }
    }

    class OnSharedElementCallback extends SharedElementCallback {
        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {

            if (sharedElement == null) {
                // If shared element is null, then it has been scrolled off screen and
                // no longer visible. In this case we cancel the shared element transition by
                // removing the shared element from the shared elements map.
                names.clear();
                sharedElements.clear();
            } else if (startPosition != currPosition) {
                // If the user has swiped to a different ViewPager page, then we need to
                // remove the old shared element and replace it with the new shared element
                // that should be transitioned instead.
                names.remove(names.indexOf(startTransitionName));
                names.add(sharedElement.getTransitionName());
                sharedElements.remove(startTransitionName);
                sharedElements.put(sharedElement.getTransitionName(), sharedElement);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinish(int translationY, View view) {
        if (currPosition >= firstPosition && currPosition <= lastPosition) {
            finishAfterTransition();
        } else {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), translationY);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    finishAfterTransition();
//                    finish();
                }
            });
            animator.setDuration(getResources().getInteger(R.integer.transitions_duration));
            animator.start();

        }

    }

    @Override
    public void finishAfterTransition() {
        sharedElement.getAttacher().cleanup();
        Intent data = new Intent();
        data.putExtra(EXTRA_CURRENT_POSITION, currPosition);
        setResult(RESULT_OK, data);
        resetToolBar();
        super.finishAfterTransition();
    }

    private void resetToolBar() {
//        getSupportActionBar().hide();
//        toolbar.setBackgroundResource(R.color.colorPrimary);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.addOnPageChangeListener(null);
    }
}
