package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.materialspinner.MaterialSpinner;
import com.example.ecommerce.adapters.CarModelsAdapter;
import com.example.ecommerce.adapters.DownloadedPhotoAdapter;
import com.example.ecommerce.adapters.GoodCategoryAdapter;
import com.example.ecommerce.adapters.SlidingViewPagerAdapter;
import com.example.ecommerce.models.Car;
import com.example.ecommerce.models.CarModels;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.models.Good;
import com.example.ecommerce.models.GoodForFree;
import com.example.ecommerce.models.User;
import com.example.ecommerce.utils.Data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.santalu.maskedittext.MaskEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ItemAddActivity extends AppCompatActivity implements View.OnClickListener {

    Good carGood = new Good();
    Good good = new Good();
    Good goodForFree = new Good();
    FrameLayout mainFrameLayout;
    MaterialSpinner categorySpinner, subSpinner, currencySpinner, region_spinner, city_spinner,
            carModelSpinner, carTypeModelSpinner, yearSpinner, bodyTypeSpinner, fuelSpinner, driveUnitSpinner, carColorSpinner,
            cppSpinner, steeringWheelSpinner, conditionSpinner, engineCapacitySpinner;
    String[] categoryModelList = Data.getGoodCategorySpinner();
    ArrayList<CategoryModel> categoryModelArrayList = Data.getGoodCategory();
    ArrayList<CarModels> carModelsArrayList = Data.getCarModel();
    private ImageButton imageButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;
    Bitmap bitmap;
    List<Uri> imagePathList = new ArrayList<>();
    FirebaseStorage storage;
    StorageReference reference;
    int go;
    Button addButton;
    RecyclerView recyclerView;
    DownloadedPhotoAdapter adapter;
    SwitchCompat switchButton, my_number_switch;
    MaterialEditText price, header, desc;
    MaskEditText phone_number;
    TextInputLayout phoneNumberInputLayout;
    ConstraintLayout price_constraint, transporConstraint;
    TextView chooseCategoryText, textPrice, textView11, textView12;
    RelativeLayout relative_dogovornaya;
    DatabaseReference goodsReference, userReference;
    FirebaseUser firebaseUser;
    String userName = "";
    List<String> imageUrlList = new ArrayList<>();

    String[] currencies = {"KGS", "USD"};

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/montserrat_regular.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    userName = user.getUsername().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        carGood.setGoodCategory("Транспорт");
        region_spinner = findViewById(R.id.region_spinner);
        city_spinner = findViewById(R.id.city_spinner);
        categorySpinner = findViewById(R.id.category_spinner);
        subSpinner = findViewById(R.id.subcategory_spinner);
        imageButton = findViewById(R.id.imageButton);
        addButton = findViewById(R.id.add_btn);
        currencySpinner = findViewById(R.id.currency_spinner);
        switchButton = findViewById(R.id.dogovornaya_switch);
        my_number_switch = findViewById(R.id.my_number_switch);
        phoneNumberInputLayout = findViewById(R.id.phoneNumberInputLayout);
        phone_number = findViewById(R.id.phone_number);
        header = findViewById(R.id.header_main);
        desc = findViewById(R.id.desc_main);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);
//        mainFrameLayout = view.findViewById(R.id.add_frame_layout);
        price = findViewById(R.id.price);
        price_constraint = findViewById(R.id.price_constraint);
        textPrice = findViewById(R.id.textPrice);
        relative_dogovornaya = findViewById(R.id.relative_dogovornaya);
        recyclerView = findViewById(R.id.photo_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ItemAddActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        chooseCategoryText = findViewById(R.id.chooseCategoryText);

        transporConstraint = findViewById(R.id.transporConstraint);
        carModelSpinner = findViewById(R.id.carModelSpinner);
        carTypeModelSpinner = findViewById(R.id.carTypeModelSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);
        bodyTypeSpinner = findViewById(R.id.bodyTypeSpinner);
        fuelSpinner = findViewById(R.id.fuelSpinner);
        driveUnitSpinner = findViewById(R.id.driveUnitSpinner);
        carColorSpinner = findViewById(R.id.carColorSpinner);
        cppSpinner = findViewById(R.id.cppSpinner);
        steeringWheelSpinner = findViewById(R.id.steeringWheelSpinner);
        conditionSpinner = findViewById(R.id.conditionSpinner);
        engineCapacitySpinner = findViewById(R.id.engineCapacitySpinner);


//        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String subCategoryText = parent.getItemAtPosition(position).toString();
//                good.setGoodSubCategory(subCategoryText);
//                car.setCarSubCategory(subCategoryText);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        currencySpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currencyText = parent.getItemAtPosition(position).toString();
                good.setGoodCurrency(currencyText);
//                car.setCarCurrency(currencyText);
                carGood.setGoodCurrency(currencyText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        city_spinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cityText = parent.getItemAtPosition(position).toString();
                good.setGoodCity(cityText);
                goodForFree.setGoodCity(cityText);
//                car.setCarCity(cityText);
                carGood.setGoodCity(cityText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        region_spinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String regionText = parent.getItemAtPosition(position).toString();
//                good.setGoodRegion(regionText);
//                goodForFree.setGoodRegion(regionText);
//                car.setCarRegion(regionText);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        carTypeModelSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarBrand(parent.getItemAtPosition(position).toString());
                carGood.setCarBrand(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        yearSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarYear(parent.getItemAtPosition(position).toString());
                carGood.setCarYear(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bodyTypeSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarBodyType(parent.getItemAtPosition(position).toString());
                carGood.setCarBodyType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                car.setCarBodyType("Седан");
            }
        });
        fuelSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarFuelType(parent.getItemAtPosition(position).toString());
                carGood.setCarFuelType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                car.setCarFuelType("Бензин");
            }
        });
        driveUnitSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarDriveUnit(parent.getItemAtPosition(position).toString());
                carGood.setCarDriveUnit(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                car.setCarDriveUnit("Передний");
            }
        });
        carColorSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarColor(parent.getItemAtPosition(position).toString());
                carGood.setCarColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                car.setCarColor("Серебристый");
            }
        });
        cppSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarCppType(parent.getItemAtPosition(position).toString());
                carGood.setCarCppType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                car.setCarCppType("Механическая");
            }
        });
        steeringWheelSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarSteeringWheel(parent.getItemAtPosition(position).toString());
                carGood.setCarSteeringWheel(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                car.setCarSteeringWheel("Слева");
            }
        });
        conditionSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarCondition(parent.getItemAtPosition(position).toString());
                carGood.setCarCondition(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                car.setCarCondition("Новое");
            }
        });
        engineCapacitySpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                car.setCarEngineCapacity(parent.getItemAtPosition(position).toString());
                carGood.setCarEngineCapacity(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                car.setCarEngineCapacity("0.1");
            }
        });


        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    price_constraint.setVisibility(View.GONE);
                    good.setGoodPrice("Договорная");
//                    car.setCarPrice("Договорная");
                    carGood.setGoodPrice("Договорная");
                } else {
                    price_constraint.setVisibility(View.VISIBLE);
                    good.setGoodPrice(price.getText().toString());
//                    car.setCarPrice(price.getText().toString());
                    carGood.setGoodPrice(price.getText().toString());
                    Log.i("SettingPrice", "first");
                }
            }
        });

        phoneNumberInputLayout.setHint("+996 (***) ** ** **");
        if (phone_number.length() <= 4) {
            phone_number.setHint("+996");
        }


        my_number_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    phone_number.setEnabled(false);
                    phone_number.setClickable(false);
                    phone_number.setFocusableInTouchMode(false);
                    phone_number.setText("556767255");
                } else {
                    phone_number.setEnabled(true);
                    phone_number.setClickable(true);
                    phone_number.setFocusableInTouchMode(true);
                    phone_number.getText().clear();
                }
            }
        });

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, currencies);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(currencyAdapter);


        imageButton.setOnClickListener(this);
        //Spinner
        subSpinner.setLabel("Подкатегория");

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
        GoodCategoryAdapter adapter = new GoodCategoryAdapter(ItemAddActivity.this, categoryModelArrayList);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setLabel("Категория");

        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getRegions());
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        region_spinner.setAdapter(regionAdapter);
        region_spinner.setLabel("Регион");
        region_spinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> cityAdapter = null;
                String name = (String) parent.getItemAtPosition(position);
                good.setGoodRegion(name);
//                car.setCarRegion(name);
                carGood.setGoodRegion(name);
                goodForFree.setGoodRegion(name);
                switch (name) {
                    case "Чуйская область":
                        cityAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getChuiCities());
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Иссык-Кульская область":
                        cityAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getIssykKulCities());
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Таласская область":
                        cityAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getTalasCities());
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Нарынская область":
                        cityAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getNarynCities());
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Джалал-Абадская область":
                        cityAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getDjalalAbadCities());
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Ошская область":
                        cityAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getOshCities());
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Баткенская область":
                        cityAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getBatkenCities());
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                }
                city_spinner.setAdapter(cityAdapter);
                city_spinner.setLabel("Город (село)");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        categorySpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<String> subAdapter = null;
                CategoryModel model = (CategoryModel) adapterView.getItemAtPosition(i);
                String name = model.getCategoryName();
                good.setGoodCategory(name);
                goodForFree.setGoodCategory(name);
//                car.setCarCategory(name);
                carGood.setGoodCategory(name);
                if (name.equals("Такси") || name.equals("Услуги")) {
                    textView11.setText("Заголовок услуги");
                    textView12.setText("Описание услуги");
                } else {
                    textView11.setText("Заголовок товара");
                    textView12.setText("Описание товара");
                }
                switch (name) {

                    case "Транспорт":
                        chooseCategoryText.setVisibility(View.VISIBLE);
                        subSpinner.setVisibility(View.VISIBLE);

                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getTransportSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (parent.getItemAtPosition(position).equals("Легковые автомобили")) {
                                    transporConstraint.setVisibility(View.VISIBLE);
                                    price_constraint.setVisibility(View.VISIBLE);
//                                    car.setCarSubCategory(parent.getItemAtPosition(position).toString());
                                    carGood.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                                    getCarDetails();
                                } else {
                                    transporConstraint.setVisibility(View.GONE);
                                    good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Недвижимость":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getHomeSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Квартиры");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Такси":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getTaxiSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Городское такси");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Электроника":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getElectronicSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Мобильные телефоны и аксессуары");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Услуги":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getServicesSubCategory());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Авто услуги");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Работа":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getWorkSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Поиск сотрудников");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                                if (parent.getItemAtPosition(position).equals("Поиск сотрудников")) {
                                    textView11.setText("Заголовок работы");
                                    textView12.setText("Описание работы");
                                } else if (parent.getItemAtPosition(position).equals("Ищу работу")) {
                                    textView11.setText("Заголовок предлагаемой услуги");
                                    textView12.setText("Описание умений и навыков");
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Личные вещи":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getPrivateSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Женская одежда");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Животные":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getAnimalSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Зоотовары");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Спорт и хобби":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getSportSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Велосипеды");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Медтовары":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getMedicalSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Маски медицинские");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Детский мир":
                        setVisibilities();
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getKidsSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        good.setGoodSubCategory("Десткая одежда и обувь");
                        subSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                good.setGoodSubCategory(parent.getItemAtPosition(position).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "Отдам даром":
                        chooseCategoryText.setVisibility(View.GONE);
                        subSpinner.setVisibility(View.GONE);
                        price_constraint.setVisibility(View.GONE);
                        textPrice.setVisibility(View.GONE);
                        relative_dogovornaya.setVisibility(View.GONE);
                        transporConstraint.setVisibility(View.GONE);
                        subAdapter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getKidsSubCategories());
                        break;
                }
                subSpinner.setAdapter(subAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImageView closeImage = findViewById(R.id.closeImage);
        closeImage.setOnClickListener(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImages();

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_fom_right, R.anim.slideout_from_left);
        transaction.replace(mainFrameLayout.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closeImage:
                startActivity(new Intent(ItemAddActivity.this, DashboardActivity.class));
                break;
            case R.id.imageButton:
                pickImageFromGallery();
                break;
        }
    }

    public void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void uploadImages() {

        if (!TextUtils.isEmpty(String.valueOf(header.getText())) && !TextUtils.isEmpty(String.valueOf(desc.getText())) && String.valueOf(phone_number.getText()).length()==19) {
            Log.i("PhoneLength", String.valueOf(phone_number.getText().length()));
            if (!TextUtils.isEmpty(carGood.getGoodPrice()) || !TextUtils.isEmpty(good.getGoodPrice()) || !TextUtils.isEmpty(goodForFree.getGoodPrice()) || price_constraint.getVisibility()==View.VISIBLE&&!TextUtils.isEmpty(String.valueOf(price.getText()))) {


                final ProgressDialog dialog = new ProgressDialog(ItemAddActivity.this);
                if (imagePathList != null) {
                    final StorageReference ref = reference.child("images/" + UUID.randomUUID().toString());
                    final List<String> urlStrings = new ArrayList<>();
                    dialog.setTitle("Загрузка...");
                    dialog.show();
                    for (int i = 0; i < imagePathList.size(); i++) {
                        Uri individualImage = imagePathList.get(i);
                        final StorageReference imageName = ref.child("Images" + individualImage.getLastPathSegment());
                        imageName.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                dialog.dismiss();
                                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageUrlList.add(String.valueOf(uri));
                                        Log.i("GETIMAGE", "GETTINGIMAGES");
                                        urlStrings.add(String.valueOf(uri));
                                        if (urlStrings.size() == imagePathList.size()) {
                                            storeLink(urlStrings);
                                        }

                                    }
                                });
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                dialog.setMessage("Загружено: " + (int) (progress) + "%");

                            }
                        });
                        go++;
                    }
                }
//        if (imageUri != null) {
//
//            StorageReference ref = reference.child("images/" + UUID.randomUUID().toString());
//
//            final ProgressDialog dialog = new ProgressDialog(ItemAddActivity.this);
//            dialog.setTitle("Загрузка...");
//            dialog.show();
//
//            ref.putFile(imageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            dialog.dismiss();
//                            Toast.makeText(ItemAddActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                            dialog.setMessage("Загружено: " + (int) (progress) + "%");
//                        }
//                    });
//
//        }
            }
            else {
                Log.i("PriceFalse", "Price False");
                Toast.makeText(ItemAddActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.i("PhoneLength", String.valueOf(phone_number.getText().length()));
            Toast.makeText(ItemAddActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
        }


    }

    private void storeLink(List<String> urlStrings) {

        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 0; i < urlStrings.size(); i++) {
            hashMap.put("ImgLink" + i, urlStrings.get(i));
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        databaseReference.push().setValue(hashMap)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    if (transporConstraint.getVisibility() == View.VISIBLE) {
                                        carGood.setUserID(String.valueOf(firebaseUser.getUid()));
                                        carGood.setUserName(userName);
                                        carGood.setGoodImagesDownloadUrl(imageUrlList);
                                        carGood.setGoodDescription(desc.getText().toString());
                                        carGood.setGoodHeader(header.getText().toString());
                                        carGood.setGoodPhoneNumber(phoneNumberFilter(phone_number.getText().toString()));
                                        if (price_constraint.getVisibility() == View.VISIBLE) {
                                            carGood.setGoodPrice(price.getText().toString());
                                            Log.i("SettingPrice", "Second");
                                        } else {
                                            carGood.setGoodPrice("Договорная");
                                        }
//                                        car.setUserID(String.valueOf(firebaseUser.getUid()));
//                                        car.setCarImagesDownloadUrl(imageUrlList);
//                                        car.setCarDescription(desc.getText().toString());
//                                        car.setCarHeader(header.getText().toString());
//                                        car.setCarPhoneNumber(phoneNumberFilter(phone_number.getText().toString()));
//                                        if (price_constraint.getVisibility() == View.VISIBLE) {
//                                            car.setCarPrice(price.getText().toString());
//                                        } else {
//                                            car.setCarPrice("Договорная");
//                                        }
                                        goodsReference = FirebaseDatabase.getInstance().getReference("Goods").child("Transport").push();
//                                        if (!TextUtils.isEmpty(carGood.getGoodHeader()) && !TextUtils.isEmpty(carGood.getGoodDescription()) && carGood.getGoodImagesDownloadUrl() != null &&
//                                                !TextUtils.isEmpty(carGood.getGoodPrice()) && carGood.getGoodPhoneNumber().length() == 13) {
                                            goodsReference.setValue(carGood).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(ItemAddActivity.this, "Объявление успешно создано", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                        } else {
//                                            Toast.makeText(ItemAddActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
//                                        }
                                        imageUrlList.clear();
                                    } else if (subSpinner.getVisibility() == View.GONE) {
                                        goodForFree.setUserID(String.valueOf(firebaseUser.getUid()));
                                        goodForFree.setUserName(userName);
                                        goodForFree.setGoodImagesDownloadUrl(imageUrlList);
                                        goodForFree.setGoodHeader(header.getText().toString());
                                        goodForFree.setGoodDescription(desc.getText().toString());
                                        goodForFree.setGoodPhoneNumber(phoneNumberFilter(phone_number.getText().toString()));
                                        goodsReference = FirebaseDatabase.getInstance().getReference("Goods").child("ForFree").push();
//                                        if (!TextUtils.isEmpty(goodForFree.getGoodHeader()) && !TextUtils.isEmpty(goodForFree.getGoodDescription()) && goodForFree.getGoodImagesDownloadUrl() != null && goodForFree.getGoodPhoneNumber().length() == 13) {
                                            goodsReference.setValue(goodForFree).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(ItemAddActivity.this, "Объявление успешно создано", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                        } else {
//                                            Toast.makeText(ItemAddActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
//                                        }
                                        imageUrlList.clear();
                                    } else {
                                        good.setUserID(String.valueOf(firebaseUser.getUid()));
                                        good.setUserName(userName);
                                        good.setGoodImagesDownloadUrl(imageUrlList);
                                        good.setGoodHeader(header.getText().toString());
                                        good.setGoodDescription(desc.getText().toString());
                                        good.setGoodPhoneNumber(phoneNumberFilter(phone_number.getText().toString()));
                                        if (price_constraint.getVisibility() == View.VISIBLE) {
                                            good.setGoodPrice(price.getText().toString());
                                        } else {
                                            good.setGoodPrice("Договорная");
                                        }
                                        goodsReference = FirebaseDatabase.getInstance().getReference("Goods").child("AnyGood").push();
//                                        if (!TextUtils.isEmpty(good.getGoodHeader()) && !TextUtils.isEmpty(good.getGoodDescription()) && good.getGoodImagesDownloadUrl() != null &&
//                                                !TextUtils.isEmpty(good.getGoodPrice()) && good.getGoodPhoneNumber().length() == 13) {
                                            goodsReference.setValue(good).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(ItemAddActivity.this, "Объявление успешно создано", Toast.LENGTH_SHORT).show();
                                                }
                                            });
//                                        } else {
//                                            Toast.makeText(ItemAddActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
//                                        }
                                        imageUrlList.clear();

                                    }
                                    Toast.makeText(ItemAddActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                }
                                Intent intent = new Intent(ItemAddActivity.this, DashboardActivity.class);
                                startActivity(intent);

                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ItemAddActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    imagePathList.clear();
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        imageUri = data.getClipData().getItemAt(i).getUri();
                        imagePathList.add(imageUri);
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                    }

                } else if (data.getData() != null) {
                    imagePathList.clear();
                    imageUri = data.getData();
                    imagePathList.add(imageUri);
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
                adapter = new DownloadedPhotoAdapter(ItemAddActivity.this, imagePathList);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new DownloadedPhotoAdapter.RecyclerOnClickListener() {
                    @Override
                    public void onClick(int position) {
                        imagePathList.remove(position);
                        adapter.notifyItemRemoved(position);
                    }


                    @Override
                    public void onSeeClick(int position) {
//                        View layout = LayoutInflater.from(ItemAddActivity.this).inflate(R.layout.slider_view, null);
//                        final Dialog dialog = new Dialog(ItemAddActivity.this);
//                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                        dialog.setContentView(layout);
//                        SliderView sliderView = layout.findViewById(R.id.imageSlider);
//                        PhotoShowDialogAdapter sliderAdapter = new PhotoShowDialogAdapter(ItemAddActivity.this, imagePathList);
//                        sliderView.setSliderAdapter(sliderAdapter);
//                        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
//                        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//                        sliderView.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                        dialog.show();


                        View layout = LayoutInflater.from(ItemAddActivity.this).inflate(R.layout.slider_view_pager, null);
                        final Dialog dialog = new Dialog(ItemAddActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(layout);
                        ViewPager viewPager = layout.findViewById(R.id.sliding_view_pager);
                        SlidingViewPagerAdapter slidingViewPagerAdapter = new SlidingViewPagerAdapter(ItemAddActivity.this, imagePathList);
                        viewPager.setAdapter(slidingViewPagerAdapter);
                        dialog.show();


//                        ImageView closingIcon = layout.findViewById(R.id.closingOpenedPhoto);
                    }
                });
            }
        }
    }

    public void setVisibilities() {
        price_constraint.setVisibility(View.VISIBLE);
        chooseCategoryText.setVisibility(View.VISIBLE);
        subSpinner.setVisibility(View.VISIBLE);
        textPrice.setVisibility(View.VISIBLE);
        relative_dogovornaya.setVisibility(View.VISIBLE);
        transporConstraint.setVisibility(View.GONE);
    }

    public void getCarDetails() {
        CarModelsAdapter carAdapter = new CarModelsAdapter(ItemAddActivity.this, carModelsArrayList);
        carModelSpinner.setAdapter(carAdapter);
        carModelSpinner.setLabel("Марка");
        carTypeModelSpinner.setLabel("Модель");
        carModelSpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ArrayAdapter<String> carTypeModelAdapter = null;
                CarModels model = (CarModels) parent.getItemAtPosition(i);
                String name = model.getCarTitle();
                switch (name) {
                    case "Acura":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getAcuraSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Alfa Romeo":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getAlfaRomeoSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Alpina":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getAlpinaSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Audi":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getAudiSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Bentley":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getBentleySeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "BMW":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getBMWSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "BYD":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getBYDSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Cadillac":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getCadillacSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Chery":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getCherySeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Chevrolet":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getChevroletSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Chrysler":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getChryslerSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Citroen":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getCitroenSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Daewoo":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getDaewooSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Daihatsu":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getDaihatsuSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Dodge":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getDodgeSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "DongFeng":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getDongFengSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Fiat":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getFiatSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Ford":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getFordSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Geely":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getGeelySeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "GMC":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getGMCSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Great Wall":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getGreatWallSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Honda":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getHondaSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Hummer":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getHummerSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Hyundai":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getHyundaiSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Infiniti":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getInfinitiSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Isuzu":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getIsuzuSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Jaguar":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getJaguarSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Jeep":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getJeepSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Kia":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getKiaSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Lamborghini":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getLamborghiniSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Land Rover":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getLandRoverSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Lexus":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getLexusSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Lifan":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getLifanSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Lincoln":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getLincolnSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Mazda":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getMazdaSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Mercedes-Benz":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getMercedesBenzSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "MINI":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getMiniCarSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Mitsubishi":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getMitsubishiSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Nissan":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getNissanSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Opel":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getOpelSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Peugeot":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getPeugeotSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Porsche":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getPorscheSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Ravon":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getRavonSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Renault":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getRenaultSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Rover":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getRoverSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Saab":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getSaabSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Saturn":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getSaturnSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Scion":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getScionSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "SEAT":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getSEATSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Skoda":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getSkodaSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "SsangYong":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getSsangYongSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Subaru":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getSubaruSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Suzuki":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getSuzukiSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Tofas":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getTofasSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Toyota":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getToyotaSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Volkswagen":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getVolkswagenSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Volvo":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getVolvoSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "ВАЗ (Lada)":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getLadaSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "ГАЗ":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getGazSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "ЗАЗ":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getZazSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "ИЖ":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getIzhSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "ЛуАЗ":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getLuazSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "Москвич":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getMoscvichSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;

                    case "УАЗ":
                        carTypeModelAdapter = new ArrayAdapter<String>(ItemAddActivity.this,
                                android.R.layout.simple_spinner_item, Data.getUazSeries());
                        carTypeModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                }
//                car.setCarModel(name);
                carGood.setCarModel(name);
                carTypeModelSpinner.setAdapter(carTypeModelAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getCarYear());
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setLabel("Год выпуска");

        ArrayAdapter<String> bodyAdapter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getCarBodyType());
        bodyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodyTypeSpinner.setAdapter(bodyAdapter);
        bodyTypeSpinner.setLabel("Тип кузова");

        ArrayAdapter<String> fuelAdapter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getFuelType());
        fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelSpinner.setAdapter(fuelAdapter);
        fuelSpinner.setLabel("Топливо");

        ArrayAdapter<String> driveUnitAdapter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getDriveUnit());
        driveUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driveUnitSpinner.setAdapter(driveUnitAdapter);
        driveUnitSpinner.setLabel("Привод");

        ArrayAdapter<String> carColorApadter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getCarColor());
        carColorApadter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carColorSpinner.setAdapter(carColorApadter);
        carColorSpinner.setLabel("Цвет");

        ArrayAdapter<String> carCPPadapter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getCppType());
        carCPPadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cppSpinner.setAdapter(carCPPadapter);
        cppSpinner.setLabel("КПП");

        ArrayAdapter<String> steeringWheelApadter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getSteeringWheel());
        steeringWheelApadter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        steeringWheelSpinner.setAdapter(steeringWheelApadter);
        steeringWheelSpinner.setLabel("Положение руля");

        ArrayAdapter<String> conditionAdapter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getCondition());
        conditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(conditionAdapter);
        conditionSpinner.setLabel("Состояние");

        ArrayAdapter<String> capacityAdapter = new ArrayAdapter<String>(ItemAddActivity.this, android.R.layout.simple_spinner_item, Data.getEngineCapacity());
        capacityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        engineCapacitySpinner.setAdapter(capacityAdapter);
        engineCapacitySpinner.setLabel("Объем двигателя");
    }

    public String phoneNumberFilter(String number) {
        String filterNumber = "";
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) != ' ' && number.charAt(i) != '(' && number.charAt(i) != ')') {
                filterNumber += number.charAt(i);
            }
        }
        return filterNumber;
    }
}