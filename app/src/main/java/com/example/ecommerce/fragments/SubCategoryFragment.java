package com.example.ecommerce.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.ecommerce.GoodsActivity;
import com.example.ecommerce.R;
import com.example.ecommerce.adapters.SecondCategoryAdapter;
import com.example.ecommerce.utils.Data;

import static android.graphics.Color.parseColor;

public class SubCategoryFragment extends Fragment {

    ListView listView;
    String categoryName = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        listView = view.findViewById(R.id.subCategoryListView);
        ArrayAdapter<String> adapter = null;
        categoryName = getArguments().getString("CategoryName");

        switch (categoryName){
            case "Транспорт":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getTransportSubCategories());

                break;
            case "Недвижимость":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getHomeSubCategories());
                break;
            case "Такси":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getTaxiSubCategories());
                break;
            case "Электроника":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getElectronicSubCategories());
                break;
            case "Услуги":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getServicesSubCategory());
                break;
            case "Работа":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getWorkSubCategories());
                break;
            case "Личные вещи":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getPrivateSubCategories());
                break;
            case "Животные":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getAnimalSubCategories());
                break;
            case "Спорт и хобби":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getSportSubCategories());
                break;
            case "Медтовары":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getMedicalSubCategories());
                break;
            case "Детский мир":
                adapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_selectable_list_item, Data.getKidsSubCategories());
                break;
            case "Отдам даром":
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                String categoryName = "Отдам даром";
                intent.putExtra("CategoryName", categoryName);
                requireActivity().startActivity(intent);
                break;
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                String subCategoryName = parent.getItemAtPosition(position).toString();
                intent.putExtra("SubCategoryName", subCategoryName);
                intent.putExtra("CategoryName", categoryName);
                requireActivity().startActivity(intent);
            }
        });
        return view;
    }
}