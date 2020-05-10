package com.example.gallerytry.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerytry.View.Category.ImageFullScreenFragment
import com.example.gallerytry.Model.AddCategoryModel
import com.example.gallerytry.R
import com.example.gallerytry.View.Category.AddImageToCategoryFragment
import com.squareup.picasso.Picasso

class CategoryAdapter(val categoryList: List<AddCategoryModel>, val mContext: Context?,val listener: CategoryCallBackListener) : RecyclerView.Adapter<CategoryAdapter.MyHolder>() {

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var categoryImage: ImageView = itemView.findViewById(R.id.categoryItemImage)
        var categoryName: TextView = itemView.findViewById(R.id.category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            LayoutInflater.from(
                mContext
            ).inflate(
                R.layout.category_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.categoryName.setText(categoryList.get(position).categoryName)
        Picasso.get().load(categoryList.get(position).categoryImage).into(holder.categoryImage)
        holder.itemView.setOnClickListener{
            listener.onClickCategory(categoryList[position].categoryName)


        }
    }
}