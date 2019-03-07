package com.dginc.deepak.retrofitexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    List<SuperHero> dataList;
    Context context;

    public  class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView superHeroName;
        public ImageView superHeroImage;
        public TextView realName;
        public TextView createdBy;


        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            superHeroName =  v.findViewById(R.id.superHeroName);
            superHeroImage = v.findViewById(R.id.superHeroImage);
            realName = v.findViewById(R.id.realName);
            createdBy = v.findViewById(R.id.createdby);
        }


    }

    public RecyclerViewAdapter(List<SuperHero> dataList, Context context){
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.superHeroName.setText(dataList.get(position).getName());
        holder.realName.setText("Real name: "+dataList.get(position).getRealname());
        holder.createdBy.setText("Created by: "+dataList.get(position).getCreatedby());
        Glide.with(context).load(dataList.get(position).getImageurl()).into(holder.superHeroImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<SuperHero> dataList) {
        this.dataList = dataList;
    }
}
