package com.example.giveawayapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
    private lateinit var controller: ItemDetailsActivityController
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

        val itemDAO = AppDatabase.getDatabase(this).itemDao()

        controller = ItemDetailsActivityController(itemDAO)
        
        setUpAdapter()

        with(binding) {

            setUpBottomNavigation(
                bottomNavigation,
                R.id.navigation_search,
            )

            itemRecyclerView.adapter = adapter
            itemRecyclerView.layoutManager = LinearLayoutManager(this@ItemDetailsActivity)

            getItemList()
        }
    }

    private fun setUpAdapter()
    {
        adapter = ItemDetailsAdapter(
            items = emptyList(),
            onCardClick = { item ->
                val intent = Intent(this@ItemDetailsActivity, ExpandedItemActivity::class.java)
                intent.putExtra("itemId", item.itemId)
                startActivity(intent)

            }
        )
    }

    private fun getItemList()
    {
        lifecycleScope.launch {

            val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("USER_ID", -1)
            val itemsList = controller.getItemList(userId)
            if (itemsList != null)
            {
                adapter.getItemList(itemsList)
            }
            else
            {
                Toast.makeText(
                    this@ItemDetailsActivity,
                    "Failed to retrieve items. Please try again later",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}