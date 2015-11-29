package com.gcm.pumsper.wsnproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Context mcontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;

//        getData();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new nodeRecycleAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

//    public void getData(String url){
//        //request GET HTTP
//
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request.Builder builder = new Request.Builder();
//        Request request = builder.url(url).build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.w("response", "fails");
//            }
//
//            @Override
//            public void onResponse(Response response) {
//                if (response.isSuccessful()) {
//                    try {
//                        updateView(response.body().string());
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.w("response", "" + e.getMessage());
//                    }
//                } else {
//                    Log.w("response", "" + response.code());
//                }
//            }
//
//            public void updateView(final String strResult) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//
//                            JSONArray ja = new JSONArray(strResult);
//                            mAdapter = new recycleAdapter(ja, type, getSupportFragmentManager());
////                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
//                            mRecyclerView.setAdapter(mAdapter);
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Log.w("response", "" + e.getMessage());
//                        }
//                    }
//                });
//            }
//        });
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
