package com.example.foodrecipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipeapp.adapter.MainCategoryAdapter
import com.example.foodrecipeapp.adapter.SubCategoryAdapter
import com.example.foodrecipeapp.database.CategoryItems
import com.example.foodrecipeapp.database.RecipeDatabase
import com.example.foodrecipeapp.databinding.ActivityHomeBinding
import com.example.foodrecipeapp.entities.Recipes
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {
    lateinit var binding:ActivityHomeBinding
    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<Recipes>()

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getDataFromDb()

        arrSubCategory.add(Recipes(1,"Beef and mustard pie"))
        arrSubCategory.add(Recipes(1,"Chicken and mushroom hotpot"))
        arrSubCategory.add(Recipes(1,"Banana pancakes"))
        arrSubCategory.add(Recipes(1,"kapsalon"))

        subCategoryAdapter.setData(arrSubCategory)



            binding.rvSubCategory.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            binding.rvSubCategory.adapter = subCategoryAdapter

    }

    private fun getDataFromDb(){
        launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategory
                arrMainCategory = cat as ArrayList<CategoryItems>
                mainCategoryAdapter.setData(arrMainCategory)
                binding.rvMainCategory.layoutManager = LinearLayoutManager(this@HomeActivity,LinearLayoutManager.HORIZONTAL,false)
                binding.rvMainCategory.adapter = mainCategoryAdapter
            }
        }
    }
}