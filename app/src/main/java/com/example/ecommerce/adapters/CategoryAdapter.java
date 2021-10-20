package com.example.ecommerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;
import com.example.ecommerce.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    List<CategoryModel> categoryModelList;
    CategoryInterface categoryInterface;

    public void setCategoryInterface(CategoryInterface categoryInterface){
        this.categoryInterface = categoryInterface;
    }

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Holder holder, int position) {
        int icon = categoryModelList.get(position).getCategoryIcon();
        String name = categoryModelList.get(position).getCategoryName();

        holder.setCategoryName(name);
        holder.setCategoryIcon(icon);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView categoryIcon;
        TextView categoryName;

        public Holder(@NonNull View itemView) {
            super(itemView);

            categoryIcon = itemView.findViewById(R.id.category_icon);
            categoryName = itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryInterface.onClick(getAdapterPosition());
                }
            });
        }

        private void setCategoryIcon(int icon){
            categoryIcon.setImageResource(icon);
        }

        private void setCategoryName(String name){
            categoryName.setText(name);
        }
    }

    public interface CategoryInterface{
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
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_category_item, parent, false);
//        return new Holder(view, categoryInterface);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SecondCategoryAdapter.Holder holder, int position) {
//        holder.categoryIcon.setImageResource(categoryModelList.get(position).getCategoryIcon());
//        holder.categoryName.setText(categoryModelList.get(position).getCategoryName());
//        holder.categoryDesc.setText(categoryModelList.get(position).getCategoryDesc());
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryModelList.size();
//    }
//
//    public static class Holder extends RecyclerView.ViewHolder {
//
//        ImageView categoryIcon;
//        TextView categoryName;
//        TextView categoryDesc;
//
//        public Holder(@NonNull View itemView, final SecondCategoryInterface categoryInterface) {
//            super(itemView);
//
//            categoryIcon = itemView.findViewById(R.id.second_image);
//            categoryName = itemView.findViewById(R.id.second_name);
//            categoryDesc = itemView.findViewById(R.id.second_desc);
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
