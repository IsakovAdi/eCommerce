package com.example.ecommerce.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.models.SecondCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class GoodCategoryAdapter extends BaseAdapter {


    ArrayList<CategoryModel> categoryModels;
    Context context;

    public GoodCategoryAdapter(Context context, ArrayList<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categoryModels.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.good_category, viewGroup, false);

        CategoryModel item = (CategoryModel) getItem(i);

        ImageView imageView = view.findViewById(R.id.good_category_image);
        TextView textView = view.findViewById(R.id.good_category_name);

        if (item != null) {
            imageView.setImageResource(item.getCategoryIcon());
            textView.setText(item.getCategoryName());
        }

        return view;
    }


//    public GoodCategoryAdapter(@NonNull Context context, ArrayList<CategoryModel> arrayList) {
//        super(context, 0, arrayList);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return initView(position, convertView, parent);
//    }
//
//    @Override
//    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return initView(position, convertView, parent);
//    }
//
//    private View initView(int position, View convertView, ViewGroup parent){
//        if (convertView == null){
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.good_category, parent, false);
//        }
//
//
//
//        CategoryModel item = getItem(position);
//
//        if (item != null) {
//            imageView.setImageResource(item.getCategoryIcon());
//            textView.setText(item.getCategoryName());
//        }
//
//        return convertView;
//    }
}
