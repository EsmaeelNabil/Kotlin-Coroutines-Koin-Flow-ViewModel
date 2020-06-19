package com.esmaeel.anim.Base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.esmaeel.anim.Koin.Network.Contract
import com.esmaeel.anim.Koin.Network.Status
import com.esmaeel.anim.R
import com.esmaeel.anim.Utils.Loader
import com.esmaeel.anim.Utils.MyUtils
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject


open class BaseActivity : AppCompatActivity() {

    private val loaderClass: Loader by inject()
    var loader: Dialog? = null
    var loaderWithTouch: Dialog? = null

    fun showLoader() {
        loader?.show()
    }

    fun showLoaderWithTouch() {
        loaderWithTouch?.show()
    }

    fun hideLoader() {
        loader?.hide()
        loaderWithTouch?.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loader = loaderClass.getLoader(this)
        loaderWithTouch = loaderClass.getLoaderWithTouch(this)
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onLoading(loaderBus: ShowLoaderBus) {
        if (loaderBus.yes) showLoader() else hideLoader()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onLoadingWithTouch(loaderBus: ShowLoaderBusWithTouch) {
        if (loaderBus.yes) showLoaderWithTouch() else hideLoader()
    }

    open fun handleErrors(contract: Contract<Any>, view: View) {
        when (contract.status) {
            Status.LOADING -> {
                showLoader()
            }
        }
    }

    private fun showErrorSnackBar(error: String) {
        TODO("Not yet implemented")
    }

    object IndefiniteSnackbar {

        private var snackbar: Snackbar? = null

        fun show(view: View, text: String?, action: () -> Unit) {
            snackbar = Snackbar.make(view, text ?: "", Snackbar.LENGTH_INDEFINITE).apply {
                setAction(view.context.getString(R.string.retry)) { action() }
                show()
            }
        }

        fun hide() {
            snackbar?.dismiss()
        }

    }


}