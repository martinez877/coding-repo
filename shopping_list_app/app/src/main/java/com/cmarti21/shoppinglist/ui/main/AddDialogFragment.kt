package com.cmarti21.shoppinglist.ui.main

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.cmarti21.shoppinglist.R
import com.cmarti21.shoppinglist.database.Item
import kotlinx.android.synthetic.main.fragment_add_dialog.*
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.*
import java.io.File
import java.util.*


class AddDialogFragment : BottomSheetDialogFragment(), AdapterView.OnItemSelectedListener {

    private val picasso = Picasso.get()
    private var newItem: Item = Item()
    private var uuid = UUID.randomUUID()
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri

    private val vm: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoFile = File(context?.applicationContext?.filesDir, "IMG_$uuid.jpg")
        photoUri = FileProvider.getUriForFile(
            requireActivity(),
            "com.cmarti21.shoppinglist.fileprovider",
            photoFile
        )

        dialog?.setOnShowListener {
            val dlg = dialog as BottomSheetDialog
            val bottomSheetInternal = dlg.findViewById<View>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal).peekHeight =
                resources.displayMetrics.heightPixels
        }

        type_spinner.onItemSelectedListener = this

        photo_imageButton.apply {
            val pm = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolveActivity =
                pm.resolveActivity(captureImage, PackageManager.MATCH_DEFAULT_ONLY)
            if (resolveActivity == null || !cameraPermission()) {
                isEnabled = false
            }
            setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                val cameraActivities =
                    pm.queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY)
                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_PREFIX_URI_PERMISSION
                    )
                    startActivityForResult(captureImage, REQUEST_PHOTO)
                }
            }
        }

        done_button.setOnClickListener {
            onDone()
            dismiss()
        }

        cancel_button.setOnClickListener {
            dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            resultCode != Activity.RESULT_OK -> return
            requestCode == REQUEST_PHOTO -> {
                requireActivity().revokeUriPermission(
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )

                if (photoFile.exists()) {
                    picasso.load(photoUri).fit().centerCrop().into(type_imageView)
                }
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent) {
            type_spinner ->
                newItem.category = parent?.getItemAtPosition(position).toString()
            }
        type_imageView.setImageResource(newItem.categoryType)

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun cameraPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun onDone() {
        newItem.uuid = uuid
        newItem.category = newItem.category
        newItem.title = title_editText.text.toString()
        newItem.store = store_editText.text.toString()
        vm.addItem(newItem)
    }

    companion object {
        private const val TAG = "AddItemFragment"
        private const val REQUEST_PHOTO = 0

        fun showDialog(fragmentManager: FragmentManager) {
            val dialog = AddDialogFragment()
            dialog.isCancelable = false
            dialog.show(
                fragmentManager,
                TAG
            )
        }
    }
}