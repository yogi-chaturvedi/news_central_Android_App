package com.newscentral.service.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.newscentral.service.rest.RestService;

/**
 * Created by Yogesh on 03-12-2016.
 */
public class HttpGet<T> extends AsyncTask<Void, Void, T> {
    private String url;
    private Class<T> responseType;
    private Context context;
    public ProgressDialog progressDialog;

    public HttpGet(Context context, String url, Class<T> responseType) {
        this.context = context;
        this.url = url;
        this.responseType = responseType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    //    showProgressDialog();
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
      //  hideProgressDialog();
    }

    @Override
    protected T doInBackground(Void... params) {
        return RestService.instance().get(url, responseType);
    }

    /*private void showProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }*/
}
