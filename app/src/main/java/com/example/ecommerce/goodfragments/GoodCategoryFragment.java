package com.example.ecommerce.goodfragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.materialspinner.MaterialSpinner;
import com.example.ecommerce.DashboardActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.GoodCategoryAdapter;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.utils.Data;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class GoodCategoryFragment extends Fragment implements View.OnClickListener {

    FrameLayout mainFrameLayout;
    MaterialSpinner categorySpinner, subSpinner;
    String[] categoryModelList = Data.getGoodCategorySpinner();
    ArrayList<CategoryModel> categoryModelArrayList = Data.getGoodCategory();
    private ImageButton imageButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_good_category, container, false);
//        mainFrameLayout = view.findViewById(R.id.add_frame_layout);

        imageButton = view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
        //Spinner
        categorySpinner = view.findViewById(R.id.category_spinner);
        subSpinner = view.findViewById(R.id.subcategory_spinner);
        subSpinner.setLabel("Подкатегория");

        GoodCategoryAdapter adapter = new GoodCategoryAdapter(getContext(), categoryModelArrayList);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setLabel("Категория");
//        categorySpinner.setError("Пожалуйста выберите категорию");

        categorySpinner.setItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<String> subAdapter = null;
                CategoryModel model = (CategoryModel) adapterView.getItemAtPosition(i);
                String name = model.getCategoryName();

                switch (name){
                    case "Транспорт":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getTransportSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Недвижимость":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getHomeSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Такси":

                        break;
                    case "Электроника":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getElectronicSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Услуги":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getServicesSubCategory());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Работа":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getWorkSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Личные вещи":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getPrivateSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Животные":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getAnimalSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Спорт и хобби":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getSportSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Медтовары":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getMedicalSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                    case "Детский мир":
                        subAdapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, Data.getKidsSubCategories());
                        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        break;
                }

                subSpinner.setAdapter(subAdapter);

                Toast.makeText(getContext(), String.valueOf(adapterView.getItemAtPosition(i)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImageView closeImage = view.findViewById(R.id.closeImage);
        closeImage.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_fom_right, R.anim.slideout_from_left);
        transaction.replace(mainFrameLayout.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.closeImage:
                startActivity(new Intent(getContext(), DashboardActivity.class));
                break;
            case R.id.imageButton:
                pickImageFromGallery();
                break;
        }
    }

    public void pickImageFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null){
            imageUri = data.getData();
        }
    }
}