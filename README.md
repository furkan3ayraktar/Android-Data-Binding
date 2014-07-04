Android Data Binding
=========
Android Data Binding library is a tool for connecting data to user interface elements. Once the layout file created and each item is tagged, one line of code binds all the data to user interface elements and saves your time for other tasks.

Current Version
----
0.1

Installation
-----------
First copy the library project's binding folder into your app's libs folder and add the following lines to your settings.gradle file.

```c
include ':app'
include ':app:libs:binding'
```

After this process, update your app's build.gradle file as following:

```c
apply plugin: 'com.android.application'

android {
    compileSdkVersion 19
    buildToolsVersion "19.1.0"

    defaultConfig {
        applicationId "com.furkanbayraktar.android.binding.sample"
        minSdkVersion 10
        targetSdkVersion 19
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            runProguard false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile project('libs:binding')
    compile 'com.android.support:appcompat-v7:19.+'
    compile 'com.mcxiaoke.volley:library:1.0.+'
}

```

Usage
------
Android Data Binding library contains a singleton object to handle data binding. It does not require initialization.  
#### Sample Data
Assume that following service call returns the following JSON object as data for binding:
```c
http://www.mocky.io/v2/53b6b7792fa6df3a15ce9230

{
  "success": true,
  "title": "Android Data Binding",
  "description": "Android Data Binding library is a tool for connecting data to user interface elements. Once the layout file created and each item is tagged, one line of code binds all the data to user interface elements and saves your time for other tasks.",
  "version": "0.1",
  "image": {
    "url": "http://blog.smartphoneslab.com/wp-content/uploads/2010/12/android-logo.png",
    "description": "Android Logo"
  },
  "link": "https://github.com/furkan3ayraktar/Android-Data-Binding",
  "action": "ListView Example"
}
```

#### Layout Tagging
The following layout file is an example for tagging according to sample data.
```c
<?xml version="1.0" encoding="utf-8"?>

<ScrollView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:background="#DCDCDC"
    tools:context=".MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:background="@android:color/white">

        <TextView
            android:tag="title"
            android:padding="10dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="17sp"
            android:textStyle="bold"
            android:singleLine="true"
            android:gravity="center_horizontal"/>

        <TextView
            android:tag="description"
            android:padding="10dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="15sp"/>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <TextView
                android:padding="10dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="15sp"
                android:textStyle="bold"
                android:text="Version: "/>

            <TextView
                android:tag="version"
                android:padding="10dp"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:textSize="15sp"/>
        </LinearLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <TextView
                android:padding="10dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="15sp"
                android:textStyle="bold"
                android:text="Service Status: "/>

            <TextView
                android:tag="success"
                android:padding="10dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="15sp"
                android:textStyle="bold"
                android:textColor="#00FF00"
                android:text="Successful"/>

            <TextView
                android:tag="!success"
                android:padding="10dp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="15sp"
                android:textStyle="bold"
                android:textColor="#FF0000"
                android:text="Failed"/>

        </LinearLayout>

        <ImageView
            android:tag="image:url"
            android:layout_margin="10dp"
            android:padding="5dp"
            android:layout_width="150dp"
            android:layout_height="150dp"
            android:layout_gravity="center_horizontal"
            android:scaleType="fitXY"
            android:background="#DCDCDC"/>

        <TextView
            android:tag="image:description"
            android:padding="10dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="12sp"
            android:textStyle="italic"
            android:singleLine="true"
            android:gravity="center_horizontal"/>

        <Button
            android:tag="action"
            android:id="@+id/btn_action"
            android:layout_margin="10dp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

    </LinearLayout>
</ScrollView>
```
#### Binding
```c
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
```
#### Handling List Items
Android Data Binding library allows you to bind specific array items to your specific UI elements.
```c
{
  "items": ["Android","Data","Binding"]
}
```

```c
<TextView
    android:tag="items[1]"
    android:padding="10dp"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textSize="17sp"
    android:textStyle="bold"
    android:singleLine="true"
    android:gravity="center_horizontal"/>
```
Binder will bind the item at index 1 to TextView and as a result TextView will have the text 'Data' in it.
#### ListView Handling
In order to explain ListView binding, let's create a simple activity which displays an rss feed.
```c
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.furkanbayraktar.android.binding.sample.ListActivity">

    <ListView
        android:tag="responseData:feed:entries"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/listView"
        android:layout_alignParentTop="true"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true" />

</RelativeLayout>

```
Set the tag of ListView as the array of items in data. 
```c
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:tag="mediaGroups[0]:contents[0]:url"
        android:layout_margin="10dp"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:background="#DCDCDC"/>

    <LinearLayout
        android:layout_margin="10dp"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:tag="publishedDate"
            android:textSize="15sp"
            android:singleLine="true"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <TextView
            android:tag="title"
            android:layout_marginTop="5dp"
            android:textStyle="bold"
            android:textSize="17sp"
            android:textColor="@android:color/black"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

    </LinearLayout>
</LinearLayout>
```
In the layout xml for rows, we tag the views according to fields in each item in items list.
```c
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
```
Just fetch the rss feed as JSON using Google's API and give the returning object to the binder. RSS reader is ready!
#### Hide/Show with Boolean Binding
If the response has fields as booleans, library uses them to show/hide UI elements.The following example is helpful to understand the concept.
```c
<TextView
    android:tag="success"
    android:padding="10dp"
    android:layout_width="wrap_content
    android:layout_height="wrap_content"
    android:textSize="15sp"
    android:textStyle="bold"
    android:textColor="#00FF00"
    android:text="Successful"/>

<TextView
    android:tag="!success"
    android:padding="10dp"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textSize="15sp"
    android:textStyle="bold"
    android:textColor="#FF0000"
    android:text="Failed"/>
```
When you bind this view to a data object which contains a field named success as boolean, the binding library will display the first TextView if the success is equal to true. If you have a boolean inside another objects of the data, you can negate it by placing the '!' at the beginning of the tag.
```c
    android:tag="!user:session:authorized"
```
This tag means that if the authorized field inside the session object of user object in the data is false, display the UI element.
#### Supported Data Types
Android Data Binding supports POJOs as data for binding. Basically all you need is to create a Java class and add getters for the fields. Furthermore, you can simply pass JSONObject as data as well. Both JSONObjects and POJOs will work with the library. 
Sample Project
----
You can find the sample project that is used to create this documentation under sample folder in this repository.
License
----
See LICENSE file