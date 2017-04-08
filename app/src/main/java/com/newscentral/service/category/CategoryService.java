package com.newscentral.service.category;

import android.content.Context;

import com.newscentral.exception.NoInternetException;
import com.newscentral.model.Category;
import com.newscentral.service.http.HttpGet;

import java.util.concurrent.ExecutionException;

import newscentral.com.newscentral.R;

/**
 * Created by Yogesh on 29-09-2016.
 */
public class CategoryService {
    public Category[] loadCategories(Context context) {
        String url = context.getString(R.string.host)+context.getString(R.string.category);
        try {
            return new HttpGet<Category[]>(context, url, Category[].class).execute().get();
        } catch (InterruptedException e) {
            throw new NoInternetException();
        } catch (ExecutionException e) {
            throw new NoInternetException();
        }
    }
}
