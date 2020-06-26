package com.cmarti21.shoppinglist.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.cmarti21.shoppinglist.R
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmarti21.shoppinglist.database.Item
import com.cmarti21.shoppinglist.databinding.ItemListBinding
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.item_list.view.*
import java.io.File


class MainFragment : Fragment() {

    private val picasso = Picasso.get()
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RecyclerAdapter

    private val vm:MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    inner class RecyclerHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val photoView: ImageView = itemView.recycler_photo_imageView
        fun bind(item: Item) {
            binding.apply {
                binding.item = item
                picasso.load(File(context?.filesDir, item.photoFileName)).placeholder(item.categoryType)
                    .fit()
                    .centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(photoView)
                executePendingBindings()
            }
        }
    }
    inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerHolder>() {
        private var items: List<Item> = emptyList()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder = RecyclerHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_list, parent, false
            )
        )

        override fun getItemCount() = items.size
        override fun onBindViewHolder(holderMedia: RecyclerHolder, position: Int) {
            val item = items[position]
            holderMedia.bind(item)
        }

        fun updateItems(newItems: List<Item>) {
            this.items = newItems
            notifyDataSetChanged()
        }

        fun getItemAtPosition(position: Int): Item {
            return items[position]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.itemLiveData.observe(viewLifecycleOwner, Observer { items -> adapter.updateItems(items) })

        adapter = RecyclerAdapter()
        recycler = view.findViewById(R.id.item_recycler)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.getItemAtPosition(viewHolder.adapterPosition)
                vm.deleteItem(item)
                itemDeletedAlert(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler)


        add_fab.setOnClickListener {
            AddDialogFragment.showDialog(requireActivity().supportFragmentManager)
        }
    }

    fun itemDeletedAlert(item: Item) {
        val msg = resources.getString(R.string.deletion_alert, item.title)
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle(R.string.alert)
            setMessage(msg)
            setIcon(R.drawable.ic_notifications_none_black_24dp)
            setPositiveButton(R.string.ok, null)
            show()
        }
    }


    companion object {
        fun newInstance() = MainFragment()

    }

}
