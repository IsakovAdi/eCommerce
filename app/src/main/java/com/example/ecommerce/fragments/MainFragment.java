package com.example.ecommerce.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.CategoryAdapter;
import com.example.ecommerce.utils.Data;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    RecyclerView categoryRecyclerView;
    CategoryAdapter categoryAdapter;
    FrameLayout parentFrameLayout;

    //Banner Slider
    ImageSlider slider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);
        parentFrameLayout = getActivity().findViewById(R.id.main_frame_layout);

        categoryAdapter = new CategoryAdapter(Data.getCategoryModels());
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        categoryAdapter.setCategoryInterface(new CategoryAdapter.CategoryInterface() {
            @Override
            public void onClick(int position) {
                if (Data.getCategoryModels().get(position).getCategoryName().equals("Все")) {
                    setFragment(new CategoryFragment());
                }
            }
        });

        slider = view.findViewById(R.id.imageSlider);
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.banner_watch, ScaleTypes.CENTER_INSIDE));
        slideModelList.add(new SlideModel(R.drawable.banner_ad, ScaleTypes.CENTER_INSIDE));
        slider.setImageList(slideModelList, ScaleTypes.CENTER_INSIDE);

        return view;
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_fom_right, R.anim.slideout_from_left);
        transaction.replace(parentFrameLayout.getId(), fragment);
        transaction.addToBackStack(null).commit();
    }
}