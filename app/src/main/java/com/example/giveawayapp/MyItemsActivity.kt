package com.example.giveawayapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.giveawayapp.controllers.MyItemsActivityController
import com.example.giveawayapp.data.AppDatabase
import com.example.giveawayapp.databinding.ActivityMyItemsBinding
import kotlinx.coroutines.launch

class MyItemsActivity : BottomNavigationActivity()
{
    private lateinit var binding: ActivityMyItemsBinding
    private var selectedCategory: String? = null
    private lateinit var controller: MyItemsActivityController

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMyItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {

            val sharedPreferences = getSharedPreferences(
                "user_prefs",
                MODE_PRIVATE
            )

            val itemDAO = AppDatabase.getDatabase(this@MyItemsActivity).itemDao()
            controller = MyItemsActivityController(itemDAO)
            setUpBottomNavigation(bottomNavigation, R.id.navigation_items, sharedPreferences)

            val dropdownCategoryMenu = dropdownCategory

            val categories = resources.getStringArray(R.array.categories_array)

            val adapter = ArrayAdapter(
                this@MyItemsActivity,
                android.R.layout.simple_dropdown_item_1line,
                categories
            )

            dropdownCategoryMenu.setAdapter(adapter)

            dropdownCategoryMenu.setOnItemClickListener { _, _, position, _ ->

                selectedCategory = categories[position]

            }

            buttonSubmit.setOnClickListener {

                val title = editTextTitle.text.toString().trim()
                val location = editTextLocation.text.toString().trim()
                val description = editTextDescription.text.toString().trim()
                val userId = sharedPreferences.getInt("USER_ID", -1)

                createItem(title, location, description, userId)
            }
        }
    }

    private fun createItem(title: String, location: String, description: String, userId: Int)
    {
        lifecycleScope.launch {
            if (!validateInputs(title, location, description))
            {
                return@launch
            }

            val success = controller.addItem(
                title = title,
                itemCategory = selectedCategory,
                description = description,
                location = location,
                userId = userId
            )
            if (success)
            {
                startActivity(Intent(this@MyItemsActivity, MainActivity::class.java))
                finish()
                Toast.makeText(this@MyItemsActivity, "Item added successfully", Toast.LENGTH_SHORT)
                    .show()
            }
            else
            {
                Toast.makeText(
                    this@MyItemsActivity,
                    "Item failed to add. Please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun validateInputs(title: String, location: String, description: String): Boolean
    {
        return when
        {
            title.isBlank() || location.isBlank() || description.isBlank() || selectedCategory.isNullOrEmpty() ->
            {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                false
            }

            else -> true
        }
    }
}