package com.example.giveawayapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.giveawayapp.databinding.ActivityMyItemsBinding

class MyItemsActivity : BottomNavigationActivity()
{
    private lateinit var binding: ActivityMyItemsBinding
    private var selectedCategory: String? = null
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
            setUpBottomNavigation(bottomNavigation, R.id.navigation_items)

            val dropdownCategoryMenu = dropdownCategory

            val categories = resources.getStringArray(R.array.categories_array)

            val adapter = ArrayAdapter(
                this@MyItemsActivity,
                android.R.layout.simple_dropdown_item_1line,
                categories
            )

            dropdownCategoryMenu.setAdapter(adapter)

            dropdownCategoryMenu.setOnItemClickListener { parent, view, position, id ->

                selectedCategory = categories[position]

            }

            buttonSubmit.setOnClickListener {

                val title = editTextTitle.text.toString().trim()
                val location = editTextLocation.text.toString().trim()
                val description = editTextDescription.text.toString().trim()

                validateInputs(title, location, description)
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