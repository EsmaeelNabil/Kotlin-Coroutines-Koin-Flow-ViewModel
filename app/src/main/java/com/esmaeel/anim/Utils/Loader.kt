package com.esmaeel.anim.Utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import com.esmaeel.anim.R
import org.koin.core.KoinComponent
import org.koin.core.inject


class Loader() {

    fun getLoader( context: Context): Dialog? {
        context?.let {
            val dialog = Dialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.loader_layout, null);
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            return dialog
        }
        return null
    }

    fun getLoaderWithTouch( context: Context): Dialog? {
        context?.let {
            val dialog = Dialog(context)
            val view =
                LayoutInflater.from(context).inflate(R.layout.loader_layout_with_touch, null);
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            return dialog
        }
        return null
    }


}