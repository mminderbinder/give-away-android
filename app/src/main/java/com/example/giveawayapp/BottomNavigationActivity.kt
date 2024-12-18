package com.example.giveawayapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BottomNavigationActivity : AppCompatActivity()
{
    protected fun setUpBottomNavigation(
        bottomNavigationView: BottomNavigationView,
        selectedItemId: Int = 0,
    )
    {
        bottomNavigationView.selectedItemId = selectedItemId

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId)
            {
                R.id.navigation_home ->
                {
                    if (this !is MainActivity)
                    {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    true
                }

                R.id.navigation_search ->
                {
                    if (this !is ItemDetailsActivity)
                    {
                        startActivity(Intent(this, ItemDetailsActivity::class.java))
                        finish()
                    }
                    true
                }

                R.id.navigation_items ->
                {
                    if (this !is MyItemsActivity)
                    {
                        startActivity(Intent(this, MyItemsActivity::class.java))
                        finish()
                    }
                    true
                }

                R.id.navigation_profile ->
                {
                    if (this !is ProfileActivity)
                    {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }
                    true
                }
                
                else -> false
            }
        }
    }
}