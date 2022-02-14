package com.masorone.wishlist.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masorone.wishlist.R
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.presentation.adapters.utils.ShopItemDiffCallback

class ShopListAdapter : ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(
    ShopItemDiffCallback()
) {
    var onShopItemLongClickListeners: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            0 -> R.layout.item_shop_disabled
            else -> R.layout.item_shop_enabled
        }
        return ShopItemViewHolder(
            LayoutInflater.from(parent.context).inflate(layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.bind(shopItem)
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    inner class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.cv_name)
        private val tvCount = itemView.findViewById<TextView>(R.id.cv_count)

        fun bind(shopItem: ShopItem) {
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()

            with(itemView) {
                setOnLongClickListener {
                    onShopItemLongClickListeners?.invoke(shopItem)
                    true
                }
                setOnClickListener {
                    onShopItemClickListener?.invoke(shopItem)
                }
            }
        }
    }

    companion object {

        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0

        const val MAX_POOL_SIZE = 10
    }
}