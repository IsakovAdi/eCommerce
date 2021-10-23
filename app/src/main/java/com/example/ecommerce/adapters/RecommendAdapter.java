package com.example.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.models.Good;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.Holder> {
    Context context;
    List<Good> goodList;

    public RecommendAdapter(Context context, List<Good> goodList) {
        this.context = context;
        this.goodList = goodList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Picasso.get().load(goodList.get(position).getGoodImagesDownloadUrl().get(0)).into(holder.imageView);
        holder.headerText.setText(goodList.get(position).getGoodHeader());
        holder.descText.setText(goodList.get(position).getGoodDescription());
        holder.price.setText(goodList.get(position).getGoodPrice());

    }

    @Override
    public int getItemCount() {
        return goodList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView headerText, descText, price;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recommendImage);
            headerText = itemView.findViewById(R.id.recommendTextHeader);
            descText = itemView.findViewById(R.id.recommendTextDesc);
            price = itemView.findViewById(R.id.recommendPrice);
        }
    }
}
