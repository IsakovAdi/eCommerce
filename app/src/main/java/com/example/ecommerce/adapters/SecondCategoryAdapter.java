package com.example.ecommerce.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.models.SecondCategoryModel;

import java.util.List;

public class SecondCategoryAdapter extends RecyclerView.Adapter<SecondCategoryAdapter.Holder> {

    List<SecondCategoryModel> categoryModelList;
    Context context;
    CategoryInterface categoryInterface;

    public void setCategoryInterface(CategoryInterface categoryInterface) {
        this.categoryInterface = categoryInterface;
    }

    public SecondCategoryAdapter(List<SecondCategoryModel> categoryModelList, Context context) {
        this.categoryModelList = categoryModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SecondCategoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_category_item, parent, false);
        return new Holder(view, categoryInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondCategoryAdapter.Holder holder, int position) {
        holder.categoryIcon.setImageResource(categoryModelList.get(position).getCategoryIcon());
        holder.categoryName.setText(categoryModelList.get(position).getCategoryName());
        holder.categoryDesc.setText(categoryModelList.get(position).getCategoryDesc());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView categoryIcon;
        TextView categoryName;
        TextView categoryDesc;
        public Holder(@NonNull View itemView, final CategoryInterface categoryInterface) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.second_image);
            categoryName = itemView.findViewById(R.id.second_name);
            categoryDesc = itemView.findViewById(R.id.second_desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryInterface.onClick(getAdapterPosition());
                }
            });
        }
    }
    public interface CategoryInterface {
        void onClick(int position);
    }
}



//    public void setCategoryInterface(SecondCategoryInterface categoryInterface){
//        this.categoryInterface = categoryInterface;
//    }
//
//    public SecondCategoryAdapter(List<SecondCategoryModel> categoryModelList) {
//        this.categoryModelList = categoryModelList;
//    }
//
//    @NonNull
//    @Override
//    public SecondCategoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SecondCategoryAdapter.Holder holder, int position) {

//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryModelList.size();
//    }
//
//    public static class Holder extends RecyclerView.ViewHolder {
//

//
//        public Holder(@NonNull View itemView, final SecondCategoryInterface categoryInterface) {
//            super(itemView);
//

//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    categoryInterface.onClick(getAdapterPosition());
//                }
//            });
//        }
//    }
//
//    public interface SecondCategoryInterface{
//        void onClick(int position);
//    }
