package com.masorone.wishlist.presentation.adapters.utils


import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView

abstract class TouchHelperSimpleCallback : SimpleCallback(
    0,
    LEFT or RIGHT
) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }
}