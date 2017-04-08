package com.newscentral.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.newscentral.model.News;
import com.newscentral.service.rest.RestService;

import newscentral.com.newscentral.R;

/**
 * Created by Yogesh on 06-12-2016.
 */
@SuppressLint("ValidFragment")
public class RecyclerViewFragment extends Fragment {

    static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    private static String name;
    private static String logoUrl;
    private static Long categoryId = null;
    private static News[] newsList = new News[0];
    //    ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private View view;
    private Context context;

    @SuppressLint("ValidFragment")
    public RecyclerViewFragment(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            categoryId = getArguments().getLong("categoryId");
        }
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        if (categoryId != null) {
            String url = context.getString(R.string.host) + context.getString(R.string.category)
                    + "/" + categoryId + context.getString(R.string.news);
            new LoadNews(context, url).execute();
        }
    }

    private void fillNewsData() {
        if (newsList.length >= 1) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            mRecyclerView.setVisibility(View.VISIBLE);
            RecyclerView.LayoutManager layoutManager;
            layoutManager = new LinearLayoutManager(context);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            //Use this now
            mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
            mAdapter = new TestRecyclerViewAdapter(context, newsList);
            mRecyclerView.setAdapter(mAdapter);
            showHideProgressBar(false);
            {
                for (int i = 0; i < ITEM_COUNT; ++i) {
                    // newsList.add(new Object());
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    void showHideProgressBar(boolean value) {
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.news_progress_bar);
        if (progressBar != null) {
            if (value) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.INVISIBLE);

            }
        }
    }

    class LoadNews extends AsyncTask<Void, Void, News[]> {
        private final Context context;
        private final String url;

        public LoadNews(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        protected void onPostExecute(News[] newsList) {
            super.onPostExecute(newsList);
            RecyclerViewFragment.newsList = newsList;
            fillNewsData();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showHideProgressBar(true);
        }

        @Override
        protected News[] doInBackground(Void... params) {
            return RestService.instance().get(url, News[].class);
        }
    }
}
