package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerce.adapters.GoodsAdapter;
import com.example.ecommerce.models.Good;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

public class SearchActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    DatabaseReference reference;
    List<Good> goodList = new ArrayList<>();
    GoodsAdapter goodsAdapter;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/montserrat_regular.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());


        getSupportActionBar().setTitle("Поиск объявления");
        swipeRefreshLayout = findViewById(R.id.searchSwipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.btnRed);
        recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.lets_icon) {
            Intent intent = new Intent(SearchActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_icon_menu).getActionView();
        searchView.setQueryHint("Поиск объявления");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchGoods(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchGoods(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchGoods(final String s) {
        swipeRefreshLayout.setRefreshing(true);
        goodList.clear();
        String[] endpoints = {"Transport", "AnyGood", "ForFree"};
        for (String endpoint : endpoints) {
            reference = FirebaseDatabase.getInstance().getReference("Goods").child(endpoint);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good = snap.getValue(Good.class);
                        if (good != null) {
                            if (good.getGoodHeader().toString().toLowerCase().matches("(.*)" + s.toLowerCase())) {
                                goodList.add(good);
                            } else if (good.getGoodHeader().toLowerCase().matches(s.toLowerCase() + "(.*)")) {
                                goodList.add(good);
                            } else if (good.getGoodHeader().toLowerCase().matches("(.*)" + s.toLowerCase() + "(.*)")) {
                                goodList.add(good);
                            }
                            else {
                            continue;
                            }
                        } else {
                            Toast.makeText(SearchActivity.this, "По вашему запросу ничего не найдено", Toast.LENGTH_SHORT).show();
                        }

                    }

                    if (goodList.size() > 0) {
                        swipeRefreshLayout.setRefreshing(false);
                        goodsAdapter = new GoodsAdapter(goodList, SearchActivity.this);
                        recyclerView.setAdapter(goodsAdapter);
                        goodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                                Good good = goodList.get(position);
                                Gson gson = new Gson();
                                String goodObject = gson.toJson(good);
                                intent.putExtra("ChoosedGood", goodObject);
                                startActivity(intent);
                            }
                        });
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(SearchActivity.this, "По вашему запросу ничего не найдено", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}