package com.example.giveawayapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.giveawayapp.controllers.ExpandedItemActivityController
import com.example.giveawayapp.data.AppDatabase
import com.example.giveawayapp.databinding.ActivityExpandedItemBinding
import com.example.giveawayapp.models.Item
import com.example.giveawayapp.utils.DateUtils
import kotlinx.coroutines.launch

class ExpandedItemActivity : BottomNavigationActivity()
{
    private lateinit var binding: ActivityExpandedItemBinding
    private lateinit var controller: ExpandedItemActivityController
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityExpandedItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itemDAO = AppDatabase.getDatabase(this).itemDao()

        controller = ExpandedItemActivityController(itemDAO)

        val selectedItemId = intent.getIntExtra("itemId", -1)

        getSelectedItem(selectedItemId)

        with(binding) {

            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            setUpBottomNavigation(
                bottomNavigationView = bottomNavigation,
                sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
            )
        }
    }

    private fun getSelectedItem(itemId: Int)
    {
        lifecycleScope.launch {

            val currentItem = controller.getSelectedItem(itemId)

            if (currentItem != null)
            {
                getSelectedItemInfo(currentItem)
            }
            else
            {
                Toast.makeText(
                    this@ExpandedItemActivity,
                    "Failed to retrieve item. Please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun getSelectedItemInfo(item: Item)
    {
        with(binding) {

            val formattedDate = DateUtils.formatDate(item.dateCreated)
            
            textViewListingTitle.text = item.title
            textViewDate.text = formattedDate
            textViewLocation.text = item.location
            textViewDescription.text = item.description
        }
    }
}