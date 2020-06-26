package com.cmarti21.shoppinglist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {
    @Insert
    fun addItem(item: Item)

    @Update
    fun updateItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Query("DELETE FROM items_table")
    fun deleteAllItems()

    @Query("SELECT * FROM items_table WHERE checked=0")
    fun getItemsNotCompleted(): LiveData<List<Item>>

    @Query("SELECT * FROM items_table ORDER BY id DESC")
    fun getAllItems(): LiveData<List<Item>>

}