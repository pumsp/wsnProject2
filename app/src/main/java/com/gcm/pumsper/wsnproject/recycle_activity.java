package com.gcm.pumsper.wsnproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class recycle_activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Context mcontext;
    private String link ;
    private int count = 0;

//    private String sensortype[] = {"Temperature","Light","Sound","Humidity","Motion","Source"};
    private JSONObject temp,light,sound,humidity,motion,source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        mcontext = this;
        link = getIntent().getStringExtra("link");
        int  position = Integer.parseInt(getIntent().getStringExtra("position"))+1;
        try{
            getSupportActionBar().setTitle("Node "+position);
        }catch(Exception e){
            e.printStackTrace();
        }
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                finish();

                startActivity(intent);
            }
        });
//        Toast.makeText(mcontext, "url = "+link, Toast.LENGTH_SHORT).show();

//        String sensortype[] = {"Temperature","Light","Sound","Humidity","Motion","Source"};
//        JSONObject temp,light,sound,humidity,motion,source;
        getData(link, "Temperature");
        getData(link, "Light");
        getData(link, "Sound");
        getData(link, "Humidity");
        getData(link, "Motion");


        mRecyclerView = (RecyclerView) findViewById(R.id.graph_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);




    }

    public void getData(String url, final String type){
        //request GET HTTP


        OkHttpClient okHttpClient = new OkHttpClient();
//        Request.Builder builder = new Request.Builder();
//        Request request = builder.url(url).build();
        Log.w("response", "url = " +url+"/measurements/channel/0/field/"+type+"?limit=100&sortOrder=desc&sd=254");

        Request request = new Request.Builder()
                .url(url + "/measurements/channel/0/field/"+type+"?limit=100&sortOrder=desc&sd=254")
//                .header("User-Agent", "OkHttp Headers.java")
//                .addHeader("Authorization", "Basic cmFzcGlwcDEyMzo=")
//                .addHeader("Accept", "application/json; q=0.5")

                .build();

//        Log.w("response", "request = "+request);

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.w("response", "fails");
                updateView("fails");

            }

            @Override
            public void onResponse(Response response) {
                if (response.isSuccessful()) {
//                    String sensortype[] = {"Temperature","Light","Sound","Humidity","Motion","Source"};
//                    JSONObject temp,light,sound,humidity,motion,source;

                    try {
                        updateView(response.body().string());

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.w("response", "" + e.getMessage());
                    }
                } else {
                    Log.w("response", "" + response.code());
                }
            }

            public void updateView(final String strResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
//                            Log.w("type",type+" === > ");
                            if(!strResult.equals("fails")){
                                switch (type){
                                    case "Temperature" : temp = new JSONObject(strResult);count++;break;
                                    case "Light" : light = new JSONObject(strResult);count++;break;
                                    case "Sound" : sound = new JSONObject(strResult);count++;break;
                                    case "Humidity" : humidity = new JSONObject(strResult);count++;break;
                                    case "Motion" :motion = new JSONObject(strResult);count++;break;
                                }
                                Log.w("count",""+count);
                                if(count == 5){
                                    Log.w("data","temp = "+temp);
                                    Log.w("data","light ="+light);
                                    Log.w("data","sound = "+sound);
                                    Log.w("data","humidity = "+humidity);
                                    Log.w("data","motion = "+motion);

                                    mAdapter = new graphRecycleAdapter(temp,light,sound,humidity,motion);
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                            else{
                                mAdapter = new graphRecycleAdapter(temp,light,sound,humidity,motion);
                                mRecyclerView.setAdapter(mAdapter);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycle_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
