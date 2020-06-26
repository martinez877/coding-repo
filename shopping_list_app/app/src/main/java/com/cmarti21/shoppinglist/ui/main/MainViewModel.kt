package com.cmarti21.shoppinglist.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmarti21.shoppinglist.database.Item
import com.cmarti21.shoppinglist.database.ItemRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    var currentProduct = MutableLiveData<Item>()

    init{
        ItemRepository.initialize(application)
    }

    private val repository = ItemRepository.get()
    val itemLiveData = repository.getAllItems()

    fun addItem(newItem:Item){
        repository.addItem(newItem)
    }

    fun deleteItem(newItem:Item){
        repository.deleteItem(newItem)
    }

    fun updateItem(newItem:Item){
        repository.updateItem(newItem)
    }

    fun select(item:Item){
        currentProduct.value = item
    }
}

