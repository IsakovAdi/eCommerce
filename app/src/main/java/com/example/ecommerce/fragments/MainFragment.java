package com.example.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.ecommerce.GoodsActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.CategoryAdapter;
import com.example.ecommerce.adapters.RecommendAdapter;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.models.Good;
import com.example.ecommerce.utils.Data;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class MainFragment extends Fragment {

    RecyclerView categoryRecyclerView, recommendRecycler;
    CategoryAdapter categoryAdapter;
    FrameLayout parentFrameLayout;
    FirebaseUser firebaseUser;
    RecommendAdapter recommendAdapter;
    DatabaseReference reference;
    List<Good> goodList = new ArrayList<>();

    //Banner Slider
    ImageSlider slider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recommendRecycler = view.findViewById(R.id.recommendRecycler);
        LinearLayoutManager recommendLayoutManager = new LinearLayoutManager(getActivity());
        recommendLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recommendRecycler.setLayoutManager(recommendLayoutManager);

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
                else {
                    Intent intent = new Intent(getActivity(), GoodsActivity.class);
                    String categoryName = Data.getCategoryModels().get(position).getCategoryName();
                    intent.putExtra("CategoryName", categoryName);
                    requireActivity().startActivity(intent);
//                    GoodsFragment goodsFragment = new GoodsFragment();
//                    String categryName = Data.getCategoryModels().get(position).getCategoryName();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("CategoryName", String.valueOf(categryName));
//                    goodsFragment.setArguments(bundle);
//                    setFragment(goodsFragment);
                }
            }
        });

        slider = view.findViewById(R.id.imageSlider);
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.banner_watch, ScaleTypes.CENTER_INSIDE));
        slideModelList.add(new SlideModel(R.drawable.banner_ad, ScaleTypes.CENTER_INSIDE));
        slider.setImageList(slideModelList, ScaleTypes.CENTER_INSIDE);
        getRecommendGoods();
        return view;
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_fom_right, R.anim.slideout_from_left);
        transaction.replace(parentFrameLayout.getId(), fragment);
        transaction.addToBackStack(null).commit();
    }

    public void getRecommendGoods(){
        goodList.clear();
        reference = FirebaseDatabase.getInstance().getReference("Goods").child("AnyGood");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren()) {
                    Good good =snap.getValue(Good.class);
                    goodList.add(good);
                }
                recommendAdapter = new RecommendAdapter(getContext(), goodList);
                recommendRecycler.setAdapter(recommendAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}