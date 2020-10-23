package com.example.ecommerce.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DownloadedPhotoAdapter extends RecyclerView.Adapter<DownloadedPhotoAdapter.Holder> {
    List<Uri> downloadedImagesUri;
    RecyclerOnClickListener listener;
    Context context;
    Uri imageUri;

    public void setOnItemClickListener(RecyclerOnClickListener listener) {
        this.listener = listener;
    }
    public DownloadedPhotoAdapter(Context context, List<Uri> downloadedImagesUri) {
        this.context = context;
        this.downloadedImagesUri = downloadedImagesUri;
    }

    @NonNull
    @Override
    public DownloadedPhotoAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false);

        return new Holder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadedPhotoAdapter.Holder holder, int position) {
            imageUri = downloadedImagesUri.get(position);
            Glide.with(context).asBitmap().load(imageUri).into(holder.downloaded_photo);


//        holder.downloaded_photo.setImageResource(downloadedImagesUri.get(position).getByteCount());
    }

    @Override
    public int getItemCount() {
        return downloadedImagesUri.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView downloaded_photo;
        ImageButton image_delete_button;

        public Holder(@NonNull View itemView, final RecyclerOnClickListener listener) {
            super(itemView);
            downloaded_photo = itemView.findViewById(R.id.downloaded_photo);
            image_delete_button = itemView.findViewById(R.id.image_delete_button);

            image_delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition());
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSeeClick(getAdapterPosition());
                }
            });
        }
    }

    public interface RecyclerOnClickListener {
        void onClick(int position);
        void onSeeClick(int position);
    }


}
