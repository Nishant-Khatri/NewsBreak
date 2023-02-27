package com.example.newsbreak;


import android.content.Context;
import android.location.GnssAntennaInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private SelectListener listener;
    ArrayList<News> arrLNews;
    Context context;

    public NewsListAdapter(Context context,SelectListener listene){
    this.arrLNews=new ArrayList<>();
    this.listener=listene;
    this.context=context;
    }

    public void updateNews( ArrayList<News> updateNews){
        arrLNews.clear();
        arrLNews.addAll(updateNews);
        notifyDataSetChanged();


    }

    @NonNull
    @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(arrLNews.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News currentitem= arrLNews.get(position);
        holder.title.setText(currentitem.title);
        holder.author.setText(currentitem.author);
        Glide.with(holder.itemView.getContext()).load(currentitem.imageurl).into(holder.image);


    }

    @Override
    public int getItemCount() {
        return arrLNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        TextView author;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            image=itemView.findViewById(R.id.image)   ;
            author=itemView.findViewById(R.id.author);
        }
    }
}
