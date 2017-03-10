package com.example.administrator.mydemos.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.administrator.mydemos.R;
import com.example.administrator.mydemos.Request.RequestManager;
import com.example.administrator.mydemos.adapter.AllDataRecyclerViewAdapter;
import com.example.administrator.mydemos.adapter.AllDataRecyclerViewAdapter.NormalViewHolder;
import com.example.administrator.mydemos.api.GankApi;
import com.example.administrator.mydemos.database.DBCommand;
import com.example.administrator.mydemos.database.SQLiteDBHelper;
import com.example.administrator.mydemos.model.AllData;
import com.example.administrator.mydemos.model.Results;
import com.example.administrator.mydemos.model.Students;
import com.example.administrator.mydemos.widget.BaseRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseAppCompatActivity
        implements AllDataRecyclerViewAdapter.onItemClickListener, BaseRecyclerView.OnScrollListener,RequestManager.ResponseCallBack<AllData> {

    private static final String TAG = "MainActivity";

    @BindView(R.id.recyclerView)
    BaseRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Context mContext;
    private AllDataRecyclerViewAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<Results> resultsList = new ArrayList<>();


    private SQLiteDBHelper mSqLiteDBHelper;
    private int page = 1;
    private boolean hasNext = true;
    private int startPosition;
    private int currPosition;
    private String startTransitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setExitSharedElementCallback(new OnSharedElementCallback());
        mContext = this;


        mSqLiteDBHelper = new SQLiteDBHelper(getApplicationContext());
//        mSqLiteDBHelper.insertStudents();
//        Log.i("MainActivity", "2");
        new DBCommand<List<Students>>() {
            @Override
            protected void onPostExecutor(List<Students> result) {
                for (Students students : result) {
//                    Log.i(TAG, students.id + ":" + students.name);
                }

            }

            @Override
            protected List<Students> doInBackGround() {
                return mSqLiteDBHelper.queryStudents();
            }
        }.executor();

        postRequest();

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void setupViews() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mAdapter = new AllDataRecyclerViewAdapter(resultsList, this);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(this);
    }



    private void postRequest() {
        RequestManager.volley()
                .GET()
                .URL(GankApi.getAllDataUrl(page))
                .addCallBack(this)
                .setTag(TAG)
                .create(AllData.class);
    }

    @Override
    public void onResponse(AllData response) {
        if (!response.getError()) {
            if (response.getResults().size() < GankApi.DATA_NUM) {
                mAdapter.setFooterViewStatus(AllDataRecyclerViewAdapter.LOAD_NO_MORE);
                hasNext = false;
            }
            resultsList.addAll(response.getResults());
            mAdapter.notifyDataSetInserted();
        }
        recyclerView.setIsInTheBottom(false);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        recyclerView.setIsInTheBottom(false);
    }


    @Override
    public void onDescClickListener(int position, View view) {
        startEachDayData(position, view);
    }

    @Override
    public void onImageClickListener(int position, View view) {
        startPhotoViewer(position, view);
//            startGirlsViewer(position, view);
    }

    @Override
    public void onReachBottom() {
        recyclerView.setIsInTheBottom(true);
        if (!hasNext)
            return;
        page++;
        postRequest();
    }

    class OnSharedElementCallback extends SharedElementCallback {
        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            if (startPosition != currPosition) {
//                View view = layoutManager.findViewByPosition(currPosition);
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(currPosition);
                if (viewHolder != null) {
//                    View sharedElement = view.findViewById(R.id.imageView);
                    View sharedElement = ((NormalViewHolder) viewHolder).getImageView();
                    /*View navigationBar = findViewById(android.R.id.navigationBarBackground);
                    View statusBar = findViewById(android.R.id.statusBarBackground);
                    View actionBar = findViewById(android.support.v7.appcompat.R.id.action_bar_container);
                    actionBar.setTransitionName(getString(R.string.trans_action_bar_name));
                    names.clear();
                    names.add(sharedElement.getTransitionName());
                    names.add(navigationBar.getTransitionName());
                    names.add(statusBar.getTransitionName());
                    names.add(actionBar.getTransitionName());
                    sharedElements.clear();
                    sharedElements.put(sharedElement.getTransitionName(), sharedElement);
                    sharedElements.put(navigationBar.getTransitionName(), navigationBar);
                    sharedElements.put(statusBar.getTransitionName(), statusBar);
                    sharedElements.put(actionBar.getTransitionName(), actionBar);*/
                    if (names.indexOf(startTransitionName) != -1)
                        names.remove(names.indexOf(startTransitionName));
                    names.add(sharedElement.getTransitionName());
                    sharedElements.remove(startTransitionName);
                    sharedElements.put(sharedElement.getTransitionName(), sharedElement);
                } else {
                    names.clear();
                    sharedElements.clear();
                }
            }
        }
    }


    @Override
    protected void setupWindowAnimations() {
//        Fade fadeOut = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade_out);
        Fade fadeIn = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade_in);
        getWindow().setExitTransition(fadeIn);
        getWindow().setEnterTransition(fadeIn);
//        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
//        getWindow().setExitTransition(slide);
        /*Slide slide = new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(android.view.Gravity.LEFT);
        slide.setMode(Visibility.MODE_OUT);
        */
        /*Fade fade = new Fade();
        fade.setDuration(1000);
        fade.setMode(Visibility.MODE_OUT);
        getWindow().setExitTransition(fade);
        Fade fadeReturn = new Fade();
        fadeReturn.setDuration(1000);
        fadeReturn.setMode(Visibility.MODE_IN);
        getWindow().setReturnTransition(fadeReturn);*/

    }


    private void startPhotoViewer(int position, View view) {
        startPosition = currPosition = position;
        Intent intent = new Intent(this, PhotoViewerActivity.class);
        intent.putParcelableArrayListExtra(PhotoViewerActivity.EXTRA_LIST, resultsList);
        intent.putExtra(PhotoViewerActivity.EXTRA_START_POSITION, position);
        intent.putExtra(PhotoViewerActivity.EXTRA_FIRST_VISIBLE_POSITION, layoutManager.findFirstVisibleItemPosition());
        intent.putExtra(PhotoViewerActivity.EXTRA_LAST_VISIBLE_POSITION, layoutManager.findLastVisibleItemPosition());
        intent.putExtra(PhotoViewerActivity.EXTRA_START_TRANSITION_NAME, startTransitionName = view.getTransitionName());
        intent.putExtra(PhotoViewerActivity.EXTRA_CURR_PAGE, page);
//        View decor = getWindow().getDecorView();
//        View navigationBar = decor.findViewById(android.R.id.navigationBarBackground);
//        View statusBar = decor.findViewById(android.R.id.statusBarBackground);
//        Pair<View, String> navigationBarElements = Pair.create(navigationBar, navigationBar.getTransitionName());
//        Pair<View, String> statusBarElements = Pair.create(statusBar, statusBar.getTransitionName());
//        Pair<View, String> toolbarElements = Pair.create((View) toolbar, toolbar.getTransitionName());
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                Pair.create(view, view.getTransitionName()));
        startActivity(intent, options.toBundle());
    }

    private void startGirlsViewer(int position, View view) {
        Intent intent = new Intent(this, GirlsPhotoViewerActivity.class);
        intent.putExtra(GirlsPhotoViewerActivity.KEY_URL, resultsList.get(position).getUrl());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, view, getString(R.string.trans_image_name));
        startActivity(intent, options.toBundle());
    }

    private void startEachDayData(int position, View view) {
        Intent intent = new Intent();
        intent.setClass(mContext, EachDayDataActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        currPosition = data.getIntExtra(PhotoViewerActivity.EXTRA_CURRENT_POSITION, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getInstance().cancelAllRequests(TAG);
    }
}
