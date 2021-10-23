package com.example.ecommerce;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.example.ecommerce.adapters.GoodsAdapter;
import com.example.ecommerce.adapters.PhotoShowDialogAdapter;
import com.example.ecommerce.adapters.SlidingViewPagerAdapter;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.models.Good;
import com.example.ecommerce.utils.Data;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static android.graphics.Color.parseColor;

public class DetailsActivity extends AppCompatActivity {
    TextView goodHeader, goodDesc, goodPhoneNumber, goodPrice, goodCurrency, goodCity, goodCategory, goodSubCategory, goodUserName;
    RecyclerView identicalGoodsRecycler;
    SliderView goodsImageSlider;
    CardView detailsGoodCharacterCard, detailsIdenticalGoodsCardView;
    TextView carModel, carBrand, carYear, carBody, carFuel, carDriveUnit, carColor, carCpp, carSteering, carCondition, carEngineCapacity;
    DatabaseReference reference;
    GoodsAdapter goodsAdapter;
    List<Good> goodList = new ArrayList<>();
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/montserrat_regular.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        String goodObject = getIntent().getStringExtra("ChoosedGood");
        final Good good = new Gson().fromJson(goodObject, Good.class);
        fab = findViewById(R.id.fabDetails);
        goodHeader = findViewById(R.id.detailsGoodHeader);
        goodDesc = findViewById(R.id.detailsGoodDescription);
        goodPhoneNumber = findViewById(R.id.detailsUserPhoneNumber);
        goodUserName = findViewById(R.id.detailsUserName);
        goodPrice = findViewById(R.id.detailsGoodPrice);
        goodCurrency = findViewById(R.id.detailsGoodCurrency);
        goodCity = findViewById(R.id.detailsGoodCity);
        goodCategory = findViewById(R.id.detailsGoodCategory);
        goodSubCategory = findViewById(R.id.detailsGoodSubCategory);
        identicalGoodsRecycler = findViewById(R.id.detailsIdenticalGoodsRecyclerView);
        identicalGoodsRecycler.setHasFixedSize(true);
        identicalGoodsRecycler.setLayoutManager(new GridLayoutManager(DetailsActivity.this, 2));

        goodsImageSlider = findViewById(R.id.detailsGoodImageSlider);

        detailsIdenticalGoodsCardView = findViewById(R.id.detailsIdenticalGoodsCardView);
        detailsGoodCharacterCard = findViewById(R.id.detailsGoodCharacterCardView);
        carModel = findViewById(R.id.detailsTransportModel);
        carBrand = findViewById(R.id.detailsCarBrand);
        carYear = findViewById(R.id.detailsCarYear);
        carBody = findViewById(R.id.detailsCarBody);
        carFuel = findViewById(R.id.detailsCarFuel);
        carDriveUnit = findViewById(R.id.detailsCarDriveUnit);
        carColor = findViewById(R.id.detailsCarColor);
        carCpp = findViewById(R.id.detailsCarCpp);
        carSteering = findViewById(R.id.detailsCarSteeringWheel);
        carCondition = findViewById(R.id.detailsCarCondition);
        carEngineCapacity = findViewById(R.id.detailsCarEngineCapacity);

        collapsingToolbarLayout = findViewById(R.id.detailsCollapsingToolBar);
        Toolbar toolbar = findViewById(R.id.detailsActivityToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


//        Good good = new Gson().fromJson(goodObject, Good.class);
        goodHeader.setText(String.valueOf(good.getGoodHeader()));
        goodDesc.setText(String.valueOf(good.getGoodDescription()));
        goodPhoneNumber.setText(String.valueOf(good.getGoodPhoneNumber()));
        goodPrice.setText(String.valueOf(good.getGoodPrice()));
        goodUserName.setText(String.valueOf(good.getUserName()));
        if (!good.getGoodPrice().equals("Договорная")) {
            goodCurrency.setText(String.valueOf(good.getGoodCurrency()));
        }
        goodCity.setText(String.valueOf(good.getGoodCity()));
        goodCategory.setText(String.valueOf(good.getGoodCategory()));
        goodSubCategory.setText(String.valueOf(good.getGoodSubCategory()));

        if (good.getGoodSubCategory().equals("Легковые автомобили")) {
            detailsGoodCharacterCard.setVisibility(View.VISIBLE);
            carModel.setText(String.valueOf(good.getCarModel()));
            carBrand.setText(String.valueOf(good.getCarBrand()));
            carYear.setText(String.valueOf(good.getCarYear()));
            carBody.setText(String.valueOf(good.getCarBodyType()));
            carFuel.setText(String.valueOf(good.getCarFuelType()));
            carDriveUnit.setText(String.valueOf(good.getCarDriveUnit()));
            carColor.setText(String.valueOf(good.getCarColor()));
            carCpp.setText(String.valueOf(good.getCarCppType()));
            carSteering.setText(String.valueOf(good.getCarSteeringWheel()));
            carCondition.setText(String.valueOf(good.getCarCondition()));
            carEngineCapacity.setText(String.valueOf(good.getCarEngineCapacity()));
            getSupportActionBar().setTitle(carModel.getText().toString());
        }
        else {
            getSupportActionBar().setTitle(goodSubCategory.getText().toString());
        }
        List<String> images = good.getGoodImagesDownloadUrl();
        final List<Uri> imagesList = new ArrayList<>();
        for (String string : images) {
            Uri uri = Uri.parse(string);
            imagesList.add(uri);
        }

        final String phone = good.getGoodPhoneNumber().toString();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });


        PhotoShowDialogAdapter sliderAdapter = new PhotoShowDialogAdapter(DetailsActivity.this, imagesList);
        goodsImageSlider.setSliderAdapter(sliderAdapter);
        goodsImageSlider.setIndicatorAnimation(IndicatorAnimationType.DROP);
        goodsImageSlider.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);    //SIMPLETRANSFORMATION
        goodsImageSlider.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        goodsImageSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = LayoutInflater.from(DetailsActivity.this).inflate(R.layout.slider_view_pager, null);
                final Dialog dialog = new Dialog(DetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(layout);
                ViewPager viewPager = layout.findViewById(R.id.sliding_view_pager);
                SlidingViewPagerAdapter slidingViewPagerAdapter = new SlidingViewPagerAdapter(DetailsActivity.this, imagesList);
                viewPager.setAdapter(slidingViewPagerAdapter);
                dialog.show();
            }
        });

        getIdenticalGoods(good);
    }

    public void getIdenticalGoods(final Good good) {
        goodList.clear();
        if (good.getGoodSubCategory().equals("Легковые автомобили")) {
            reference = FirebaseDatabase.getInstance().getReference("Goods").child("Transport");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good1 = snap.getValue(Good.class);
                        if (good1!=good) {
                            goodList.add(good1);
                        }
                    }
                    if (goodList.size() > 0) {
                        detailsIdenticalGoodsCardView.setVisibility(View.VISIBLE);
                        goodsAdapter = new GoodsAdapter(goodList, DetailsActivity.this);
                        identicalGoodsRecycler.setAdapter(goodsAdapter);
                        goodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(DetailsActivity.this, DetailsActivity.class);
                                Good good = goodList.get(position);
                                Gson gson = new Gson();
                                String goodObject = gson.toJson(good);
                                intent.putExtra("ChoosedGood", goodObject);
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else if (good.getGoodCategory().equals("Отдам даром")) {
            reference = FirebaseDatabase.getInstance().getReference("Goods").child("ForFree");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good1 = snap.getValue(Good.class);
                        if (good1!=good) {
                            goodList.add(good1);
                        }
                    }
                    if (goodList.size() > 0) {
                        detailsIdenticalGoodsCardView.setVisibility(View.VISIBLE);
                        goodsAdapter = new GoodsAdapter(goodList, DetailsActivity.this);
                        identicalGoodsRecycler.setAdapter(goodsAdapter);
                        goodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(DetailsActivity.this, DetailsActivity.class);
                                Good good = goodList.get(position);
                                Gson gson = new Gson();
                                String goodObject = gson.toJson(good);
                                intent.putExtra("ChoosedGood", goodObject);
                                startActivity(intent);
                            }
                        });
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
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Good good1 = snap.getValue(Good.class);
                        if (good1.getGoodSubCategory().equals(good.getGoodSubCategory())) {
                            if (good1!=good) {
                                goodList.add(good1);
                            }
                        }
                    }

                    if (goodList.size() > 0) {
                        detailsIdenticalGoodsCardView.setVisibility(View.VISIBLE);
                        goodsAdapter = new GoodsAdapter(goodList, DetailsActivity.this);
                        identicalGoodsRecycler.setAdapter(goodsAdapter);
                        goodsAdapter.setOnItemClickListener(new GoodsAdapter.RecyclerOnClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(DetailsActivity.this, DetailsActivity.class);
                                Good good = goodList.get(position);
                                Gson gson = new Gson();
                                String goodObject = gson.toJson(good);
                                intent.putExtra("ChoosedGood", goodObject);
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


}