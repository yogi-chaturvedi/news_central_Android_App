package com.newscentral.service.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.newscentral.service.rest.RestService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

/**
 * Created by Yogesh on 03-12-2016.
 */
public class HttpExchange<T> extends AsyncTask<Void, Void, T> {
    private Context context;
    private String url;
    private Class<T> responseType;
    private HttpMethod httpMethod;
    private HttpEntity entity;

    public HttpExchange(Context context, String url, Class<T> responseType, HttpMethod httpMethod, HttpEntity entity) {
        this.context = context;
        this.url = url;
        this.responseType = responseType;
        this.httpMethod = httpMethod;
        this.entity = entity;
    }

    @Override
    protected T doInBackground(Void... params) {
        return (T) RestService.instance().exchange(url, httpMethod, entity, responseType).getBody();
    }
}
