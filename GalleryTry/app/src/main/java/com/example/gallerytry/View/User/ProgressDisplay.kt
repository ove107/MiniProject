package com.example.gallerytry.View.User



import android.app.Activity
import android.app.AlertDialog
import com.example.gallerytry.R
import kotlinx.android.synthetic.main.progress_dialog.view.*

class ProgressDisplay(activity: Activity) {
    private val mActivity = activity
    private lateinit var dialog: AlertDialog
    fun startLoadingDialog(dialogMessage : String){
        val builder = AlertDialog.Builder(mActivity)
        val inflater = mActivity.layoutInflater
        val view = inflater.inflate(R.layout.progress_dialog,null)
        val text =view.progressText
        text.text = dialogMessage
        builder.setView(view)
        dialog = builder.create()
        dialog.show()
    }
    fun dismissDialog(){
        dialog.dismiss()
    }
}
