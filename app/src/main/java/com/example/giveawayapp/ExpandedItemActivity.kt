package com.example.giveawayapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.giveawayapp.controllers.ExpandedItemActivityController
import com.example.giveawayapp.data.AppDatabase
import com.example.giveawayapp.databinding.ActivityExpandedItemBinding
import com.example.giveawayapp.models.Item
import com.example.giveawayapp.utils.DateUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                bottomNavigationView = bottomNavigation
            )
            buttonContact.setOnClickListener {
                showContactUserDialog()
            }
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

            Glide.with(imageViewItemImage.context)
                .load(item.imageUrl)
                .error(R.drawable.baseline_broken_image_24)
                .into(imageViewItemImage)

            setUpFullScreenImage(item)

        }
    }

    private fun setUpFullScreenImage(item: Item)
    {

        binding.imageViewItemImage.setOnClickListener {
            val intent = Intent(this, FullScreenImageActivity::class.java)
            intent.putExtra("imageUrl", item.imageUrl)
            intent.putExtra("itemDescription", item.description)
            startActivity(intent)
        }
    }

    private fun showContactUserDialog()
    {

        MaterialAlertDialogBuilder(this)
            .setTitle("Contact User")
            .setMessage("How would you like to contact the user?")
            .setNeutralButton("Email") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}