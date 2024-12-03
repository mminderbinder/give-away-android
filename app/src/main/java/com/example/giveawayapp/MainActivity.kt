package com.example.giveawayapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giveawayapp.controllers.MainActivityController
import com.example.giveawayapp.data.AppDatabase
import com.example.giveawayapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : BottomNavigationActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: MainActivityController
    private lateinit var adapter: ItemDetailsAdapter
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("USER_ID", -1)

        println("Current User ID: $userId")

        val itemDAO = AppDatabase.getDatabase(this).itemDao()

        controller = MainActivityController(itemDAO)

        setUpAdapter()

        with(binding) {

            setUpBottomNavigation(
                bottomNavigation,
                R.id.navigation_home
            )

            buttonAddItem.setOnClickListener {
                startActivity(Intent(this@MainActivity, MyItemsActivity::class.java))
            }

            itemRecyclerView.adapter = adapter
            itemRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            getUserItems()
        }
    }

    private fun setUpAdapter()
    {
        adapter = ItemDetailsAdapter(
            emptyList(),
            onCardClick = { item ->
                val intent = Intent(this@MainActivity, MyItemsActivity::class.java)
                intent.putExtra("itemId", item.itemId)
                startActivity(intent)
            }
        )
    }

    private fun getUserItems()
    {
        val userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("USER_ID", -1)

        lifecycleScope.launch {
            val userItems = controller.getUserItems(userId)

            with(binding) {

                when
                {
                    userItems == null ->
                    {
                        Toast.makeText(
                            this@MainActivity,
                            "Failed to retrieve your items. Please try again",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    userItems.isEmpty() ->
                    {
                        viewGroup.visibility = View.VISIBLE
                    }

                    else ->
                    {
                        viewGroup.visibility = View.GONE
                        adapter.getItemList(userItems)
                    }
                }
            }
        }
    }
}