package com.example.foodrecipeapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodrecipeapp.database.Category


@Dao
interface RecipeDao {

    @get:Query("Select * From category ORDER BY id DESC")
    var getAllCategory: List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Int)

    @Query("DELETE FROM categoryitems")
    suspend fun clearDb()
}