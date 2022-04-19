package com.masorone.wishlist.domain.model

data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = DEFAULT_ID
) {

    companion object {

        const val DEFAULT_ID = 0
    }
}
