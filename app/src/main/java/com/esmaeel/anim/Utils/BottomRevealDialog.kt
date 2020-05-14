package com.esmaeel.anim.Utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.transition.TransitionManager
import com.esmaeel.anim.R

class BottomRevealDialog(private val activity: Activity) {
    private val background = RelativeLayout(activity)
    private lateinit var childView: ViewGroup

    init {
        background.setBackgroundColor(ContextCompat.getColor(activity,
            R.color.blackTransparent
        ))
        setCancelable(true)
        background.setOnClickListener { dismiss() }
    }
    fun setView(view: ViewGroup): BottomRevealDialog {
        childView = view
        setToBottom(view)
        return this
    }

    private fun setToBottom(view: View) {
        val param = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, view.id)
        background.addView(view, param)
    }

    fun setCancelable(value: Boolean): BottomRevealDialog {
        background.isClickable = value
        background.isFocusable = value
        return this
    }

    fun autoTransition() {
        autoTransition(childView)
    }

    private fun autoTransition(view: ViewGroup) {
        TransitionManager.beginDelayedTransition(view)
    }

    fun show(): BottomRevealDialog {
        activity.window.decorView.findViewById<ViewGroup>(android.R.id.content).addView(background)
        return this
    }

    fun dismiss(): BottomRevealDialog {
        activity.window.decorView.findViewById<ViewGroup>(android.R.id.content).removeView(background)
        return this
    }
}
