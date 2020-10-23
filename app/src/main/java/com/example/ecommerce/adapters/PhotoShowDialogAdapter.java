package com.example.ecommerce.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class PhotoShowDialogAdapter extends SliderViewAdapter<PhotoShowDialogAdapter.SliderAdapterVH> {
    List<Uri> showingImages;
    Context context;

    public PhotoShowDialogAdapter(Context context, List<Uri> showingImages){
        this.context = context;
        this.showingImages = showingImages;
    }

//    public void newItems(List<Uri> sliderImages){
//        this.showingImages = sliderImages;
//        notifyDataSetChanged();
//    }
//
//    public void deleteImages(int position){
//        this.showingImages.remove(position);
//        notifyDataSetChanged();
//    }
//
//    public void addImages(Uri sliderImages){
//        this.showingImages.add(sliderImages);
//        notifyDataSetChanged();
//    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_images_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        Uri sliderImageUri = showingImages.get(position);
//        Glide.with(context).asBitmap().load(sliderImageUri).into(viewHolder.slidingImage);
        Glide.with(viewHolder.itemView)
                .load(sliderImageUri)
                .centerInside()
                .into(viewHolder.slidingImage);
    }

    @Override
    public int getCount() {
        return showingImages.size();
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView slidingImage;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            slidingImage = itemView.findViewById(R.id.seeSlidingPhoto);
            this.itemView = itemView;
        }
    }
}
