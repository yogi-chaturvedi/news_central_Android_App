package com.newscentral.service.news;

import android.content.Context;

import com.newscentral.exception.NoInternetException;
import com.newscentral.model.News;
import com.newscentral.service.http.HttpGet;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import newscentral.com.newscentral.R;

/**
 * Created by Yogesh on 06-12-2016.
 */
public class NewsService {
    public ArrayList<News> loadNewsList(Context context, Long categoryId) {
        String url = context.getString(R.string.host) + context.getString(R.string.category)
                + "/" + categoryId + context.getString(R.string.news);
        try {
            ArrayList<News> newsList = new ArrayList<>();
            News[] newses = new HttpGet<News[]>(context, url, News[].class).execute().get();
            for (News news : newses) {
                newsList.add(news);
            }
            return newsList;
        } catch (InterruptedException e) {
            throw new NoInternetException();
        } catch (ExecutionException e) {
            throw new NoInternetException();
        }
    }
}
