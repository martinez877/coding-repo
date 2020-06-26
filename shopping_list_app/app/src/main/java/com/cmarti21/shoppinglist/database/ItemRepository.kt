package com.cmarti21.shoppinglist.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.Executors

class ItemRepository private constructor(context: Context) {

    private val database: ItemDatabase = Room.databaseBuilder(
        context.applicationContext,
        ItemDatabase::class.java,
        "myapp_database"
    )
        .addMigrations(migration_1_2)
        .build()

    private val itemDao = database.itemDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun addItem(item: Item) {
        executor.execute {
            itemDao.addItem(item)
        }
    }

    fun updateItem(item: Item) {
        executor.execute {
            itemDao.updateItem(item)
        }
    }

    fun deleteItem(item: Item) {
        executor.execute {
            itemDao.deleteItem(item)
        }
    }

    fun deleteAllItems() = itemDao.deleteAllItems()

    fun getItemsNotCompleted(): LiveData<List<Item>> = itemDao.getItemsNotCompleted()

    fun getAllItems(): LiveData<List<Item>> = itemDao.getAllItems()

    companion object {

        private var INSTANCE: ItemRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ItemRepository(context)
            }
        }

        fun get(): ItemRepository {
            return INSTANCE
                ?: throw IllegalStateException("ItemRepository must be initialized.")
        }
    }
}