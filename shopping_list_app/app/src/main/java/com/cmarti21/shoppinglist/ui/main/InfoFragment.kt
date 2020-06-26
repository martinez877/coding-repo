package com.cmarti21.shoppinglist.ui.main

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import com.cmarti21.shoppinglist.BuildConfig
import com.cmarti21.shoppinglist.R
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //build_time_textView.text = BuildConfig.BUILD_TIME
        title_textView.text = resources.getString(R.string.app_name)
        version_textView.text = BuildConfig.VERSION_NAME
        copyright_textView.text = resources.getString(R.string.copyright)

        if (BuildConfig.DEBUG) {
            debug_build_textView.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}
