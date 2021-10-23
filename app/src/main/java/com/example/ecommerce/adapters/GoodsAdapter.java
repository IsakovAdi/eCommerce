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

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.Holder> {
    List<Good> goodList;
    Context context;
    RecyclerOnClickListener listener;

    public void setOnItemClickListener(RecyclerOnClickListener listener) {
        this.listener = listener;
    }

    public GoodsAdapter(List<Good> goodList, Context context) {
        this.goodList = goodList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.good_category_item, parent, false);
        return new Holder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Picasso.get().load(goodList.get(position).getGoodImagesDownloadUrl().get(0)).into(holder.imageView);
        holder.headerText.setText(goodList.get(position).getGoodHeader());
        holder.price.setText(goodList.get(position).getGoodPrice());
    }

    @Override
    public int getItemCount() {
        return goodList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView headerText, descText, price;

        public Holder(@NonNull View itemView, final RecyclerOnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.good_image_item);
            headerText = itemView.findViewById(R.id.good_header_item);
            price = itemView.findViewById(R.id.good_price_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface RecyclerOnClickListener {
        void onClick(int position);
    }
}
