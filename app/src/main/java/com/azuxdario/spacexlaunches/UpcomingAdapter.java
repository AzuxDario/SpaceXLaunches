package com.azuxdario.spacexlaunches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder> {
    private ArrayList<UpcomingItem> upcomingList;

    public static class UpcomingViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;

        public UpcomingViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }

    public UpcomingAdapter(ArrayList<UpcomingItem> upcomingList) {
        this.upcomingList = upcomingList;
    }

    @Override
    public UpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_item, parent, false);
        UpcomingViewHolder uvh = new UpcomingViewHolder(v);
        return uvh;
    }
    @Override
    public void onBindViewHolder(UpcomingViewHolder holder, int position) {
        UpcomingItem currentItem = upcomingList.get(position);

        holder.textView1.setText(currentItem.getText1());
        holder.textView2.setText(currentItem.getText2());
    }
    @Override
    public int getItemCount() {
        return upcomingList.size();
    }
}
