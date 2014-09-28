package com.furkanbayraktar.databindingprivate.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.furkanbayraktar.databinding.ViewBinder;
import com.furkanbayraktar.databinding.annotations.BindedFunction;
import com.furkanbayraktar.databindingprivate.app.model.sample.SampleObject;
import com.furkanbayraktar.databindingprivate.app.network.GsonRequest;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeRequest();
    }

    private void makeRequest() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        String url = "http://www.mocky.io/v2/542827842232a54505d1a3d9";

        GsonRequest<SampleObject> myReq = new GsonRequest<SampleObject>(
                Request.Method.GET,
                url,
                SampleObject.class,
                new Response.Listener<SampleObject>() {
                    @Override
                    public void onResponse(SampleObject sampleObject) {
                        try {
                            ViewBinder.getInstance().bindView(sampleObject, MainActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String errorMsg = volleyError.getLocalizedMessage();

                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG)
                                .show();

                    }
                });

        mRequestQueue.add(myReq);
    }

    @BindedFunction
    public void showNews(String alertTitle, String alertDescription){
        new AlertDialog.Builder(this)
                .setTitle(alertTitle)
                .setMessage(alertDescription)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(MainActivity.this, ListActivity.class));
                    }
                })
                .show();
    }

    @BindedFunction
    public void loggingToggled(Boolean isChecked){
        ViewBinder.getInstance().setLoggingEnabled(isChecked);
    }
}
