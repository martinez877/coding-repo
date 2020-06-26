package com.cmarti21.shoppinglist.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cmarti21.shoppinglist.R
import java.util.*

@Entity(tableName = "items_table")
data class Item (

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var title: String = "",
    var category: String = "",
    var store: String = "",
    var checked: Int = 0,
    var uuid: UUID = UUID.randomUUID()
)
{
    val photoFileName
        get() = "IMG_$uuid.jpg"

    val categoryType
        get() = when (category) {
            "Fruit" -> R.drawable.fruit_icon
            "Vegetable" -> R.drawable.vegetable_icon
            "Dairy" -> R.drawable.dairy_icon
            "Meat" -> R.drawable.meat_icon
            "Dessert" -> R.drawable.dessert_icon
            "Pet Food" -> R.drawable.peetfood_icon
            "Cleaning Supplies" -> R.drawable.cleaning_item
            else -> R.drawable.precooked_food
        }

//        val ratingImage
//        get() = when (rating) {
//            0 -> R.drawable.stars_1
//            1 -> R.drawable.stars_2
//            2 -> R.drawable.stars_3
//            3 -> R.drawable.stars_4
//            else -> R.drawable.stars_5
//        }
}