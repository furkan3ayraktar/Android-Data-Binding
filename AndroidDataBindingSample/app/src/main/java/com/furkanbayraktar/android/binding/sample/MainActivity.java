package com.furkanbayraktar.android.binding.sample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.furkanbayraktar.android.binding.BindingManager;

import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JsonObjectRequest request = new JsonObjectRequest("http://www.mocky.io/v2/53b6b7792fa6df3a15ce9230",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        VolleyLog.v("Response: %n %s", jsonObject.toString());
                        try {
                            BindingManager.getBinder().bindActivity(jsonObject, MainActivity.this);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        VolleyLog.e("Error: ", volleyError.getMessage());
                    }
                }
        );

        Volley.newRequestQueue(this).add(request);

        findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
    }

}
