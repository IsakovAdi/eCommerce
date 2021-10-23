package com.example.ecommerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.adapters.GoodsAdapter;
import com.example.ecommerce.models.Good;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GoodsFragment extends Fragment {
    RecyclerView goodsRecyclerView;
    GoodsAdapter goodsAdapter;
    DatabaseReference reference;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Good> goodList = new ArrayList<>();
    String categoryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods, container, false);

//        categoryName = getArguments().getString("CategoryName");
//
//        swipeRefreshLayout = view.findViewById(R.id.goodsSwipeRefresh);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//        goodsRecyclerView = view.findViewById(R.id.goodsRecyclerView);
//        goodsRecyclerView.setHasFixedSize(true);
//        goodsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getGoods();
//            }
//        });
//
//        getGoods();


        return view;
    }

//    public void getGoods() {
//        swipeRefreshLayout.setRefreshing(true);
//        goodList.clear();
//        if (categoryName.equals("Транспорт")) {
//            reference = FirebaseDatabase.getInstance().getReference("Goods").child("Transport");
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    swipeRefreshLayout.setRefreshing(false);
//                    for (DataSnapshot snap : snapshot.getChildren()) {
//                        Good good = snap.getValue(Good.class);
//                        goodList.add(good);
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//            reference = FirebaseDatabase.getInstance().getReference("Goods").child("AnyGood");
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot snap : snapshot.getChildren()) {
//                        Good good = snap.getValue(Good.class);
//                        if (good.getGoodCategory().equals("Транспорт")) {
//                            goodList.add(good);
//                        }
//                    }
//
//                    if (goodList.size() > 0) {
//                        goodsAdapter = new GoodsAdapter(goodList, getContext());
//                        goodsRecyclerView.setAdapter(goodsAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Категория " + categoryName + " пуста", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//        } else if (categoryName.equals("Отдам даром")) {
//            reference = FirebaseDatabase.getInstance().getReference("Goods").child("ForFree");
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    swipeRefreshLayout.setRefreshing(false);
//                    for (DataSnapshot snap : snapshot.getChildren()) {
//                        Good good = snap.getValue(Good.class);
//                        goodList.add(good);
//                    }
//                    if (goodList.size() > 0) {
//                        goodsAdapter = new GoodsAdapter(goodList, getContext());
//                        goodsRecyclerView.setAdapter(goodsAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Категория " + categoryName + " пуста", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        } else {
//            reference = FirebaseDatabase.getInstance().getReference("Goods").child("AnyGood");
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    swipeRefreshLayout.setRefreshing(false);
//                    for (DataSnapshot snap : snapshot.getChildren()) {
//                        Good good = snap.getValue(Good.class);
//                        if (good.getGoodCategory().equals(categoryName)) {
//                            goodList.add(good);
//                        }
//                    }
//                    if (goodList.size() > 0) {
//                        goodsAdapter = new GoodsAdapter(goodList, getContext());
//                        goodsRecyclerView.setAdapter(goodsAdapter);
//                    } else {
//                        Toast.makeText(getActivity(), "Категория " + categoryName + " пуста", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//
//    }
}