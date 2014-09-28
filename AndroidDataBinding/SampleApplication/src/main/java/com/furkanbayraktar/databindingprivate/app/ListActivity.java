package com.furkanbayraktar.databindingprivate.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.furkanbayraktar.databinding.ViewBinder;
import com.furkanbayraktar.databinding.annotations.BindedFunction;
import com.furkanbayraktar.databindingprivate.app.model.news.ResponseObject;
import com.furkanbayraktar.databindingprivate.app.network.GsonRequest;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/19/14.
 * Copyright Valensas A.S.
 */
public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list);

        loadAPI();
    }

    @BindedFunction
    public void rowAction(String link){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    private void loadAPI() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = "https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=100&q=http://rss.hurriyet.com.tr/rss.aspx?sectionId=2035";

        GsonRequest<ResponseObject> myReq = new GsonRequest<ResponseObject>(
                Request.Method.GET,
                url,
                ResponseObject.class,
                new Response.Listener<ResponseObject>() {
                    @Override
                    public void onResponse(ResponseObject response) {
                        try {
                            ViewBinder.getInstance().bindView(response, ListActivity.this, R.layout.row);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMsg = error.getLocalizedMessage();

                        Toast.makeText(ListActivity.this, errorMsg, Toast.LENGTH_LONG)
                                .show();
                    }
                });

        mRequestQueue.add(myReq);
    }
}
