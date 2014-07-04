package com.furkanbayraktar.android.binding.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.furkanbayraktar.android.binding.BindingManager;
import com.furkanbayraktar.android.binding.sample.R;

import org.json.JSONException;
import org.json.JSONObject;

public class WebActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        JSONObject item = null;

        try {
            item = ListActivity.objects.getJSONObject(getIntent().getIntExtra("index", 0));
            BindingManager.getBinder().bindActivity(item,this);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
