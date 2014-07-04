package com.furkanbayraktar.android.binding.sample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.furkanbayraktar.android.binding.BindingManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListActivity extends ActionBarActivity {

    public static JSONArray objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        JsonObjectRequest request = new JsonObjectRequest("https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=100&q=http://rss.hurriyet.com.tr/rss.aspx?sectionId=2035",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        VolleyLog.v("Response: %n %s", jsonObject.toString());
                        try {
                            BindingManager.getBinder().bindActivity(jsonObject, ListActivity.this, R.layout.list_row);
                            objects = jsonObject.getJSONObject("responseData").getJSONObject("feed").getJSONArray("entries");
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

        ((ListView) findViewById(R.id.listView)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListActivity.this,WebActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });
    }
}
