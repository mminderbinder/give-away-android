package com.example.giveawayapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.giveawayapp.databinding.ItemsLayoutBinding
import com.example.giveawayapp.models.Item
import com.example.giveawayapp.utils.DateUtils

class ItemDetailsAdapter(
    private var items: List<Item>,
    private val onCardClick: (Item) -> Unit
) :
    RecyclerView.Adapter<ItemDetailsAdapter.ViewHolder>()
{

    class ViewHolder(val binding: ItemsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val binding = ItemsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val currentItem = items[position]
        with(holder.binding) {

            val formattedDate = DateUtils.formatDate(currentItem.dateCreated)

            textViewTitle.text = currentItem.title
            textViewCategory.text = currentItem.itemCategory.name
            textViewDescriptionContent.text = currentItem.description
            textViewLocation.text = currentItem.location
            textViewDate.text = formattedDate

            itemCardView.setOnClickListener {
                onCardClick(currentItem)
            }
        }
    }

    fun getItemList(itemList: List<Item>)
    {
        items = itemList
        notifyDataSetChanged()
    }
}