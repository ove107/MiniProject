package com.example.gallerytry.View

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.gallerytry.R
import com.example.gallerytry.ViewModel.GalleryViewModel
import kotlinx.android.synthetic.main.user_profile.view.*

class DialogLogout: DialogFragment() {

    private lateinit var viewmodel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.logout_backpress_dialog,container,false)
        viewmodel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        val btnCancel = view.findViewById<View>(R.id.buttonCancel) as Button
        val btnAccept = view.findViewById<View>(R.id.buttonok) as Button
        btnCancel.setOnClickListener {
            Toast.makeText(activity, "action cancelled", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        btnAccept.setOnClickListener {
            Toast.makeText(activity, "Logging Out", Toast.LENGTH_SHORT).show()
            dismiss()
            clearBackStack()
                viewmodel.logout()
                startActivity(
                    Intent(context,
                        MainActivity::class.java)
                )

        }

        return view

    }
    private fun clearBackStack() {
        for(i in 1..fragmentManager?.backStackEntryCount!!)
            fragmentManager!!.popBackStack()
    }
}