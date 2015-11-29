package com.gcm.pumsper.wsnproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private String [] title = {"อุณหภูมิ","แสงสว่าง","เสียง","การเคลื่อนไหว","อะไรดี"};
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
    public void onBindViewHolder(ViewHolder holder, int position) {

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

//        NumberFormat nf = NumberFormat.getInstance();
//        nf.setMinimumFractionDigits(3);
//        nf.setMinimumIntegerDigits(2);
//
//        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));


        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(holder.graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {" ", "TIME"});
//        staticLabelsFormatter.setVerticalLabels(new String[] {"low", "middle", "high"});
        holder.graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

//        series.setOnDataPointTapListener(new OnDataPointTapListener() {
//            @Override
//            public void onTap(Series series, DataPointInterface dataPoint) {
//                Toast.makeText(getApplicationContext(), "Series1: On Data Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public LineGraphSeries<DataPoint> mapData(JSONObject data){
        int temp [] = {1,5,3,2,6};
        try{
            if(data != null){
                //            JSONArray ar = data.getJSONArray("e");

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, temp[0]),
                new DataPoint(1, temp[1]),
                new DataPoint(2, temp[2]),
                new DataPoint(3, temp[3]),
                new DataPoint(4, temp[4])
        });
        return series;

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}