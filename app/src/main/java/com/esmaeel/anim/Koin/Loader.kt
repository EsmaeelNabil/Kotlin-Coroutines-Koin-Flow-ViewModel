package com.esmaeel.anim.Koin

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import com.esmaeel.anim.R


class Loader(val context: Context?) {

    fun getLoader(): Dialog? {
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

    fun getLoaderWithTouch(): Dialog? {
        context?.let {
            val dialog = Dialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.loader_layout_with_touch, null);
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            return dialog
        }
        return null
    }


}