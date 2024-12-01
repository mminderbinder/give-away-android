package com.example.giveawayapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giveawayapp.controllers.ItemDetailsActivityController
import com.example.giveawayapp.data.AppDatabase
import com.example.giveawayapp.databinding.ActivitySearchItemsBinding
import kotlinx.coroutines.launch

class ItemDetailsActivity : BottomNavigationActivity()
{
    private lateinit var itemDetailsActivityController: ItemDetailsActivityController
    private lateinit var binding: ActivitySearchItemsBinding
    private lateinit var adapter: ItemDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // TODO: Centralize initialization
        val sharedPreferences = getSharedPreferences(
            "user_prefs",
            MODE_PRIVATE
        )

        adapter = ItemDetailsAdapter(emptyList())

        val itemDAO = AppDatabase.getDatabase(this).itemDao()
        itemDetailsActivityController = ItemDetailsActivityController(itemDAO)


        with(binding) {

            setUpBottomNavigation(bottomNavigation, R.id.navigation_search, sharedPreferences)
            itemRecyclerView.adapter = adapter
            itemRecyclerView.layoutManager = LinearLayoutManager(this@ItemDetailsActivity)

            getItemList()
        }
    }


    private fun getItemList()
    {
        lifecycleScope.launch {

            val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("USER_ID", -1)
            val itemsList = itemDetailsActivityController.getItemList(userId)
            adapter.getItemList(itemsList)
        }
    }
}