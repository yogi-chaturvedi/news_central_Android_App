package com.newscentral.service.http;

import android.content.Context;
import android.os.AsyncTask;

import com.newscentral.service.rest.RestService;


/**
 * Created by Yogesh on 03-12-2016.
 */
public class HttpPost<T> extends AsyncTask<Void, Void, T> {
    private String url;
    Object request;
    private Class<T> responseType;

    public HttpPost(Context context, String url, Object request, Class<T> responseType) {
        this.url = url;
        this.request = request;
        this.responseType = responseType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
    }

    @Override
    protected T doInBackground(Void... params) {
        return RestService.instance().post(this.url, request, responseType);
    }
}
