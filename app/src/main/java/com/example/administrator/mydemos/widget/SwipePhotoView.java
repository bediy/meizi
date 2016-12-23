package com.example.administrator.mydemos.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * TODO: document your custom view class.
 */
public class SwipePhotoView extends ImageView {

    private static final String TAG = SwipePhotoView.class.getSimpleName();

    private PhotoViewAttacher attacher;


    public SwipePhotoView(Context context) {
        super(context);
    }

    public SwipePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipePhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PhotoViewAttacher getAttacher() {
        return attacher;
    }

    public void setAttacher(PhotoViewAttacher attacher) {
        this.attacher = attacher;
    }
}
