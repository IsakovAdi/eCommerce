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

import com.example.ecommerce.R;
import com.example.ecommerce.adapters.SecondCategoryAdapter;
import com.example.ecommerce.utils.Data;


public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    FrameLayout parentFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        //View elements
        recyclerView = view.findViewById(R.id.recyclerView);
        parentFrameLayout = getActivity().findViewById(R.id.main_frame_layout);

        SecondCategoryAdapter secondAdapter = new SecondCategoryAdapter(Data.getSecondCategory());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(secondAdapter);
        secondAdapter.notifyDataSetChanged();

        return view;
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_fom_right, R.anim.slideout_from_left);
        transaction.replace(parentFrameLayout.getId(), fragment);
        transaction.commit();
    }
}