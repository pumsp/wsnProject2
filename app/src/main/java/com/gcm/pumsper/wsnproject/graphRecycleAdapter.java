package com.gcm.pumsper.wsnproject;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONObject;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by pumsper on 29/11/2558.
 */
public class graphRecycleAdapter extends RecyclerView.Adapter<graphRecycleAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private GraphView graph;

        public ViewHolder(View v) {
            super(v);
            graph = (GraphView) v.findViewById(R.id.graph);
        }
    }

    private Context mcontext ;
    private String [] title = {"อุณหภูมิ","แสงสว่าง","เสียง","ความชื้น","การเคลื่อนไหว"};
//    private ArrayList<DataPoint> temp = new ArrayList<>();
//    private ArrayList<DataPoint> light = new ArrayList<>();
//    private ArrayList<DataPoint> sound = new ArrayList<>();
//    private ArrayList<DataPoint> humidity = new ArrayList<>();
//    private ArrayList<DataPoint> motion = new ArrayList<>();

    private JSONObject temp,light,sound,humidity,motion;

    public graphRecycleAdapter(JSONObject temp,JSONObject light,JSONObject sound,JSONObject humidity,JSONObject motion){
        this.temp = temp;
        this.light = light;
        this.sound = sound;
        this.humidity = humidity;
        this.motion = motion;
    }

    public graphRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.graph_card, parent, false);

        final ViewHolder holder = new ViewHolder(v);
        mcontext = v.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        switch (position){
            case 0:series = mapData(temp);break;
            case 1:series = mapData(light);break;
            case 2:series = mapData(sound);break;
            case 3:series = mapData(humidity);break;
            case 4:series = mapData(motion);break;
        }
        holder.graph.setTitle(title[position]);

        holder.graph.addSeries(series);


        SimpleDateFormat dateFormat= new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
        String cDateTime = dateFormat.format(new Date());
        Log.w("time",cDateTime);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(holder.graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{" ", "TIME"});
//        staticLabelsFormatter.setVerticalLabels(new String[] {"low", "middle", "high"});
        holder.graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);


        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                String type = "";
                switch (position){
                    case 0:type="TEMP : ";break;
                    case 1:type="LIGHT : ";break;
                    case 2:type="SOUND : ";break;
                    case 3:type="HUMIDITY : ";break;
                    case 4:type="MOTION : ";break;
                }
                Toast.makeText(mcontext, type+ dataPoint.getY(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public LineGraphSeries<DataPoint> mapData(JSONObject data){
        DataPoint[] d = new DataPoint[1];
        try{
            if(data != null){
                JSONArray ar = data.getJSONArray("e");
                d = new DataPoint[ar.length()];
                Log.w("array",""+ar);
                for(int i = 0 ; i < ar.length() ; i++){
                    JSONObject js = ar.getJSONObject(i);
//                    Log.w("object",""+js);
                    double s = Double.parseDouble(js.getString("v"));
                    d[i] = new DataPoint(i,s);
                    Log.w("datapoint",""+d[i]);
                }
//                Log.w("datapoint",""+d));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(d);
        return series;

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}