package com.gcm.pumsper.wsnproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class nodeRecycleAdapter extends RecyclerView.Adapter<nodeRecycleAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.textView);
        }
    }
    private Context mcontext ;

    private String [] device = {"https://gekko.tinamous.com/api/v1/devices/7e78ed34-15de-44bb-b7d9-3ae464035653",
            "https://gekko.tinamous.com/api/v1/devices/d72ed9b9-e9a1-498d-b5b2-d894ce33842b",
            "https://gekko.tinamous.com/api/v1/devices/93344de1-0645-45fe-895d-6ba42f913aa4",
            "https://gekko.tinamous.com/api/v1/devices/e51635b9-5d24-47d2-a618-c78800c8f303",
            "https://gekko.tinamous.com/api/v1/devices/5e9c236a-334d-4aca-9e85-103683bc9e5b",
            " https://gekko.tinamous.com/api/v1/devices/29f78bd6-e616-4f18-8985-f0c7e979ddde"};

    @Override
    public nodeRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_card, parent, false);

        final ViewHolder holder = new ViewHolder(v);
        mcontext = v.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(nodeRecycleAdapter.ViewHolder holder, final int position) {
        holder.text.setText("Node "+(position+1));

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(mcontext, "I'am node "+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext, recycle_activity.class);
                intent.putExtra("link", "" + device[position]);
                intent.putExtra("position",""+position);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return device.length;
    }



}
