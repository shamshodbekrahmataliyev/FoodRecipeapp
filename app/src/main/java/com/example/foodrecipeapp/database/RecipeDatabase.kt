package com.example.foodrecipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodrecipeapp.dao.RecipeDao
import com.example.foodrecipeapp.entities.Recipes
import com.example.foodrecipeapp.entities.converter.CategoryListConverter


@Database(entities = [Recipes::class,CategoryItems::class,Category::class,CategoryListConverter::class],version = 1, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase(){

    companion object{

        var recipiesDatabase:RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context):RecipeDatabase{
            if (recipiesDatabase == null){
                    recipiesDatabase = Room.databaseBuilder(
                        context,
                        RecipeDatabase::class.java,
                        "recipe.db"
                    ).build()
            }
            return recipiesDatabase!!
        }
    }
    abstract fun recipeDao():RecipeDao
}