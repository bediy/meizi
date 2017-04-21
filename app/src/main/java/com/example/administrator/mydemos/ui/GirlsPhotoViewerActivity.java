package com.example.administrator.mydemos.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.administrator.mydemos.R;
import com.example.administrator.mydemos.Request.RequestManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GirlsPhotoViewerActivity extends AppCompatActivity implements View.OnTouchListener {

    private final static String TAG = "GirlsPhotoViewerActivity";

    @BindView(R.id.fullImageView)
    ImageView imageView;
    PhotoViewAttacher attacher;

    public final static String KEY_URL = "key_url";
    private int mLastY;
    private int mOffSetY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girls_photo_viewer);
        postponeEnterTransition();
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupWindowAnimations();
        Intent intent = getIntent();

        attacher = new PhotoViewAttacher(imageView);
        /*Glide
                .with(this)
                .load(intent.getStringExtra(KEY_URL))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        attacher.update();
                        scheduleStartPostponedTransition(imageView);
                    }
                });*/
        RequestManager.loadImage(
                this,
                intent.getStringExtra(KEY_URL),
                imageView,
                new RequestManager.ResourceCallBack() {
                    @Override
                    public void onImageReady() {
                        attacher.update();
                        scheduleStartPostponedTransition(imageView);
                    }
                });
        imageView.setOnTouchListener(this);
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

    private void setupWindowAnimations() {
//        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade_in);
//        getWindow().setEnterTransition(fade);
        /*Fade fade = new Fade();
        fade.setDuration(500);
        getWindow().setEnterTransition(fade);*/

//        Slide slide = new Slide();
//        slide.setDuration(500);
//        getWindow().setReturnTransition(slide);
        /*TransitionSet transform = (TransitionSet) TransitionInflater.from(this).inflateTransition(R.transition.change_image_transform);
        getWindow().setSharedElementEnterTransition(transform);
        getWindow().setSharedElementReturnTransition(transform);
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);*/
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
    public boolean onTouch(View v, MotionEvent event) {
        if (attacher.getScale() == PhotoViewAttacher.DEFAULT_MIN_SCALE) {
            int y = (int) event.getRawY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mOffSetY = 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int deltaY = y - mLastY;
                    int translationY = (int) (v.getTranslationY() + deltaY);
                    v.setTranslationY(translationY);
                    mOffSetY = mOffSetY + deltaY;
                    break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs(mOffSetY) > 300) {
                        finishAfterTransition();
                    } else {
                        int currTranslationY = (int) v.getTranslationY();
                        ObjectAnimator.ofFloat(v, "translationY", currTranslationY, 0).start();
                    }
                    break;
                default:
                    break;
            }
            mLastY = y;
        }
        attacher.onTouch(v, event);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        attacher.cleanup();
    }
}
