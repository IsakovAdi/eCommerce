package com.example.ecommerce.fragments;

import android.content.Intent;
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

import com.example.ecommerce.DetailsActivity;
import com.example.ecommerce.GoodsActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.GoodsAdapter;
import com.example.ecommerce.models.Good;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AdFragment extends Fragment {
    RecyclerView myGoodsRecyclerView;
    GoodsAdapter myGoodsAdapter;
    DatabaseReference reference;
    FirebaseUser firebaseUser;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Good> goodList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ad, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        swipeRefreshLayout = view.findViewById(R.id.myGoodSwipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        myGoodsRecyclerView = view.findViewById(R.id.myGoodsRecyclerView);
        myGoodsRecyclerView.setHasFixedSize(true);
        myGoodsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyGoods();
            }
        });
        getMyGoods();
        return view;
    }

    public void getMyGoods() {
        swipeRefreshLayout.setRefreshing(true);
        goodList.clear();
        reference = FirebaseDatabase.getInstance().getReference("Goods").child("AnyGood");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                swipeRefreshLayout.setRefreshing(false);
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Good good = snap.getValue(Good.class);
                    if (good.getUserID().equals(firebaseUser.getUid())) {
                        goodList.add(good);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference("Goods").child("Transport");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Good good = snap.getValue(Good.class);
                    if (good.getUserID().equals(firebaseUser.getUid())) {
                        goodList.add(good);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference = FirebaseDatabase.getInstance().getReference("Goods").child("ForFree");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Good good = snap.getValue(Good.class);
                    if (good.getUserID().equals(firebaseUser.getUid())) {
                        goodList.add(good);
                    }
                }
                if (goodList.size() > 0) {
                    myGoodsAdapter = new GoodsAdapter(goodList, getContext());
                    myGoodsRecyclerView.setAdapter(myGoodsAdapter);
                    myGoodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(getContext(), DetailsActivity.class);
                            Good good = goodList.get(position);
                            Gson gson = new Gson();
                            String goodObject = gson.toJson(good);
                            intent.putExtra("ChoosedGood", goodObject);
                            requireActivity().startActivity(intent);
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "У вас нет объявлений", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}