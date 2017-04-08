package com.newscentral.base;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.newscentral.model.Category;
import com.newscentral.service.category.CategoryService;
import com.newscentral.service.rest.RestService;

import newscentral.com.newscentral.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // Load Categories
        String url = getString(R.string.host)+getString(R.string.category);
        new LoadCategory(SplashActivity.this,url).execute();
    }

    void showHideProgressBar(boolean value) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.splash_progess_bar);
        if (progressBar != null) {
            if (value) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    class LoadCategory extends AsyncTask<Void, Void, Category[]> {
        private final Context context;
        private final String url;

        public LoadCategory (Context context, String url) {
            this.context = context;
            this.url = url;
        }
        @Override
        protected void onPostExecute(Category[] categories) {
            super.onPostExecute(categories);
            BaseActivity.categories = categories;
            showHideProgressBar(false);
            Intent intent = new Intent(SplashActivity.this, BaseActivity.class);
            SplashActivity.this.finish();
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.overridePendingTransition(0, 0);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showHideProgressBar(true);
        }

        @Override
        protected Category[] doInBackground(Void... params) {
            return RestService.instance().get(url, Category[].class);
        }
    }
}
