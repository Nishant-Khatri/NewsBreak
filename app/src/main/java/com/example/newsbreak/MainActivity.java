package com.example.newsbreak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.Throws;


public class MainActivity extends AppCompatActivity implements SelectListener{
    RecyclerView recyclerView;
    NewsListAdapter nv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData();
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         nv = new NewsListAdapter(this,this);
        recyclerView.setAdapter(nv);
    }
    private void fetchData(){
        String url= "https://newsapi.org/v2/top-headlines?country=in&apiKey=510da84140a240ba84beb77c6382dccf";


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray newsJsonArray=response.getJSONArray("articles");
                            ArrayList<News> newsArray=new ArrayList<>();
                            for(int i=0;i<newsJsonArray.length();i++){
                                JSONObject newsJsonObject=newsJsonArray.getJSONObject(i);
                                News n=new News(newsJsonObject.getString("title"),
                                        newsJsonObject.getString("author"),
                                        newsJsonObject.getString("url"),
                                        newsJsonObject.getString("urlToImage"));
                                newsArray.add(n);
                            }
                            nv.updateNews(newsArray);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("User-agent", "Mozilla/5.0");
                return headers;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemClicked(News als) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(als.url));
        Toast.makeText(this, "Opening ", Toast.LENGTH_SHORT).show();

    }
}