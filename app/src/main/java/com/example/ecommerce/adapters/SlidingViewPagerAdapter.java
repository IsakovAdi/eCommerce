package com.example.ecommerce.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.ecommerce.R;

import java.util.List;

public class SlidingViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Uri> slidingImages;
    private LayoutInflater layoutInflater;


    public SlidingViewPagerAdapter(Context context, List<Uri> slidingImages) {
        this.context = context;
        this.slidingImages = slidingImages;
    }

    @Override
    public int getCount() {
        return slidingImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(ConstraintLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.slider_images_layout, container, false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.seeSlidingPhoto);
        Glide.with(item_view)
                .load(slidingImages.get(position))
                .centerInside()
                .into(imageView);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

    public interface SlidingInterface{
        void onClick(int position);
    }
}
