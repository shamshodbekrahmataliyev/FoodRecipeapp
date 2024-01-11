package com.example.foodrecipeapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodrecipeapp.database.RecipeDatabase
import com.example.foodrecipeapp.databinding.ActivitySplashBinding
import com.example.foodrecipeapp.entities.CategoryItems
import com.example.foodrecipeapp.interfaces.GetDataService
import com.example.foodrecipeapp.retrofitclient.RetrofitClientInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity(), EasyPermissions.RationaleCallbacks, EasyPermissions.PermissionCallbacks {
    private lateinit var binding: ActivitySplashBinding
    private val Read_STORAGE_PERM = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readStorageTask()

        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

     fun getCategories() {
        val service = RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<CategoryItems> {
            override fun onResponse(call: Call<CategoryItems>, response: Response<CategoryItems>) {
                insertDataIntoRoomDb(response.body())
            }

            override fun onFailure(call: Call<CategoryItems>, t: Throwable) {
                binding.loader.visibility = View.INVISIBLE
                 Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun insertDataIntoRoomDb(category: CategoryItems?) {
        GlobalScope.launch(Dispatchers.IO) {
            category?.let {
                RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().clearDb()
                for (arr in it.indices!!) {
                    RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().insertCategory(arr)
                }
                runOnUiThread {
                    binding.btnGetStarted.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun readStorageTask() {
        if (!hasReadStoragePermission()) {
            getCategories()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage",
                Read_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleAccepted(requestCode: Int) {
        // Handle rationale accepted
    }

    override fun onRationaleDenied(requestCode: Int) {
        // Handle rationale denied
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        // Handle permissions denied
    }
}
