package com.example.foodrecipeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipeapp.databinding.ItemRvMainCategoryBinding
import com.example.foodrecipeapp.entities.CategoryItems

class MainCategoryAdapter: RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {
    var arrMainCategory = ArrayList<CategoryItems>()
    class RecipeViewHolder(val binding:ItemRvMainCategoryBinding):RecyclerView.ViewHolder(binding.root){

    }

    fun setData(arrData : List<CategoryItems>){
        arrMainCategory = arrData as ArrayList<CategoryItems>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRvMainCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return arrMainCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.binding.tvDishName.text = arrMainCategory[position].strcategory



    }
}