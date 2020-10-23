package com.example.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.example.ecommerce.models.CarModels;
import com.example.ecommerce.models.CategoryModel;

import java.util.ArrayList;

public class CarModelsAdapter extends BaseAdapter {
    ArrayList<CarModels> carModelsArrayList;
    Context context;

    public CarModelsAdapter( Context context, ArrayList<CarModels> carModelsArrayList) {
        this.carModelsArrayList = carModelsArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return carModelsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return carModelsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.good_category, viewGroup, false);
        CarModels item = (CarModels) getItem(i);

        ImageView imageView = view.findViewById(R.id.good_category_image);
        TextView textView = view.findViewById(R.id.good_category_name);

        if (item != null) {
            imageView.setImageResource(item.getCarIcon());
            textView.setText(item.getCarTitle());
        }

        return view;
    }
}
