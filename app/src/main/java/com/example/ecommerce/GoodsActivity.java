package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.adapters.GoodsAdapter;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.models.Good;
import com.example.ecommerce.utils.Data;
import com.google.android.material.appbar.CollapsingToolbarLayout;
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

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.graphics.Color.parseColor;

public class GoodsActivity extends AppCompatActivity {

    RecyclerView goodsRecyclerView;
    GoodsAdapter goodsAdapter;
    DatabaseReference reference;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Good> goodList = new ArrayList<>();
    String categoryName;
    TextView categoryDescription;
    ImageView categoryIcon;
    String subCategoryName;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/montserrat_regular.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        categoryDescription = findViewById(R.id.categoryDesc);
        categoryIcon = findViewById(R.id.categoryImage);
        categoryName = getIntent().getStringExtra("CategoryName");
        subCategoryName = getIntent().getStringExtra("SubCategoryName");
        swipeRefreshLayout = findViewById(R.id.goodsSwipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        goodsRecyclerView = findViewById(R.id.goodsRecyclerView);
        goodsRecyclerView.setHasFixedSize(true);
        goodsRecyclerView.setLayoutManager(new GridLayoutManager(GoodsActivity.this, 2));

        toolbar = findViewById(R.id.goodsActivityToolbar);
        collapsingToolbarLayout = findViewById(R.id.detailsCollapsingToolBar);

        if (subCategoryName == null) {
            settingColors();
            getGoods();
        } else {
            settingColors();
            getSubGoods();
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {


            for (CategoryModel model : Data.getCategoryModels()) {
                if (model.getCategoryName().equals(categoryName)) {
//                    getSupportActionBar().setDisplayShowTitleEnabled(true);
//                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                    toolbar.setNavigationIcon(model.getCategoryIcon());
                    if (subCategoryName == null) {
                        getSupportActionBar().setTitle(model.getCategoryName());
                    } else {
                        getSupportActionBar().setTitle(subCategoryName);
                    }
                    categoryIcon.setImageResource(model.getCategoryIcon());
                    categoryDescription.setText(model.getCategoryDescription());

//                    getSupportActionBar().set

                }
            }
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (subCategoryName == null) {
                    getGoods();
                } else {
                    getSubGoods();
                }
            }
        });


    }

    public void getGoods() {
        swipeRefreshLayout.setRefreshing(true);
        goodList.clear();
        if (categoryName.equals("Транспорт")) {
            reference = FirebaseDatabase.getInstance().getReference("Goods").child("Transport");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    swipeRefreshLayout.setRefreshing(false);
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good = snap.getValue(Good.class);
                        goodList.add(good);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            reference = FirebaseDatabase.getInstance().getReference("Goods").child("AnyGood");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good = snap.getValue(Good.class);
                        if (good.getGoodCategory().equals("Транспорт")) {
                            goodList.add(good);
                        }
                    }

                    if (goodList.size() > 0) {
                        goodsAdapter = new GoodsAdapter(goodList, GoodsActivity.this);
                        goodsRecyclerView.setAdapter(goodsAdapter);
                        goodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(GoodsActivity.this, DetailsActivity.class);
                                Good good = goodList.get(position);
                                Gson gson = new Gson();
                                String goodObject = gson.toJson(good);
                                intent.putExtra("ChoosedGood", goodObject);
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(GoodsActivity.this, "Категория " + categoryName + " пуста", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else if (categoryName.equals("Отдам даром")) {
            reference = FirebaseDatabase.getInstance().getReference("Goods").child("ForFree");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    swipeRefreshLayout.setRefreshing(false);
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good = snap.getValue(Good.class);
                            goodList.add(good);
                    }
                    if (goodList.size() > 0) {
                        goodsAdapter = new GoodsAdapter(goodList, GoodsActivity.this);
                        goodsRecyclerView.setAdapter(goodsAdapter);
                        goodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(GoodsActivity.this, DetailsActivity.class);
                                Good good = goodList.get(position);
                                Gson gson = new Gson();
                                String goodObject = gson.toJson(good);
                                intent.putExtra("ChoosedGood", goodObject);
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(GoodsActivity.this, "Категория " + categoryName + " пуста", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            reference = FirebaseDatabase.getInstance().getReference("Goods").child("AnyGood");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    swipeRefreshLayout.setRefreshing(false);
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good = snap.getValue(Good.class);
                        if (good.getGoodCategory().equals(categoryName)) {
                                goodList.add(good);
                        }
                    }
                    if (goodList.size() > 0) {
                        goodsAdapter = new GoodsAdapter(goodList, GoodsActivity.this);
                        goodsRecyclerView.setAdapter(goodsAdapter);
                        goodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(GoodsActivity.this, DetailsActivity.class);
                                Good good = goodList.get(position);
                                Gson gson = new Gson();
                                String goodObject = gson.toJson(good);
                                intent.putExtra("ChoosedGood", goodObject);
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(GoodsActivity.this, "Категория " + categoryName + " пуста", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    public void getSubGoods() {
        swipeRefreshLayout.setRefreshing(true);
        goodList.clear();

        if (subCategoryName.equals("Легковые автомобили")) {
            reference = FirebaseDatabase.getInstance().getReference("Goods").child("Transport");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    swipeRefreshLayout.setRefreshing(false);
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good = snap.getValue(Good.class);
                            goodList.add(good);
                    }
                    if (goodList.size() > 0) {
                        goodsAdapter = new GoodsAdapter(goodList, GoodsActivity.this);
                        goodsRecyclerView.setAdapter(goodsAdapter);
                        goodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(GoodsActivity.this, DetailsActivity.class);
                                Good good = goodList.get(position);
                                Gson gson = new Gson();
                                String goodObject = gson.toJson(good);
                                intent.putExtra("ChoosedGood", goodObject);
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(GoodsActivity.this, "Категория " + subCategoryName + " пуста", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            reference = FirebaseDatabase.getInstance().getReference("Goods").child("AnyGood");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    swipeRefreshLayout.setRefreshing(false);
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good = snap.getValue(Good.class);
                        if (good.getGoodSubCategory().equals(subCategoryName)) {
                                goodList.add(good);
                        }
                    }
                    if (goodList.size() > 0) {
                        goodsAdapter = new GoodsAdapter(goodList, GoodsActivity.this);
                        goodsRecyclerView.setAdapter(goodsAdapter);
                        goodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(GoodsActivity.this, DetailsActivity.class);
                                Good good = goodList.get(position);
                                Gson gson = new Gson();
                                String goodObject = gson.toJson(good);
                                intent.putExtra("ChoosedGood", goodObject);
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(GoodsActivity.this, "Категория " + subCategoryName + " пуста", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void settingColors() {
        switch (categoryName) {
            case "Транспорт":
                toolbar.setBackgroundColor(parseColor("#009BFE"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#009BFE"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#009BFE"));
                break;
            case "Недвижимость":
                toolbar.setBackgroundColor(parseColor("#FD009B"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#FD009B"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#FD009B"));
                break;
            case "Такси":
                toolbar.setBackgroundColor(parseColor("#FDD701"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#FDD701"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#FDD701"));
                break;
            case "Электроника":
                toolbar.setBackgroundColor(parseColor("#A100FE"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#A100FE"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#A100FE"));
                break;
            case "Услуги":
                toolbar.setBackgroundColor(parseColor("#E0C801"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#E0C801"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#E0C801"));
                break;
            case "Работа":
                toolbar.setBackgroundColor(parseColor("#50B952"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#50B952"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#50B952"));
                break;
            case "Личные вещи":
                toolbar.setBackgroundColor(getResources().getColor(R.color.green, getTheme()));
                collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.green, getTheme()));
                collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.green, getTheme()));
                break;
            case "Животные":
                toolbar.setBackgroundColor(parseColor("#00A7FE"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#00A7FE"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#00A7FE"));
                break;
            case "Спорт и хобби":
                toolbar.setBackgroundColor(parseColor("#FDAD01"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#FDAD01"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#FDAD01"));
                break;
            case "Медтовары":
                toolbar.setBackgroundColor(parseColor("#FD0001"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#FD0001"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#FD0001"));
                break;
            case "Детский мир":
                toolbar.setBackgroundColor(parseColor("#E70068"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#E70068"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#E70068"));
                break;
            case "Отдам даром":
                toolbar.setBackgroundColor(parseColor("#C70202"));
                collapsingToolbarLayout.setBackgroundColor(parseColor("#C70202"));
                collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#C70202"));
                break;
        }
    }

}
