package com.esmaeel.anim.Base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esmaeel.anim.Koin.Loader
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


public open class BaseActivity : AppCompatActivity() {
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
        loader = Loader(this).getLoader();
        loaderWithTouch = Loader(this).getLoaderWithTouch();
    }

    override fun onResume() {
        super.onResume()
        loader = Loader(this).getLoader();
        loaderWithTouch = Loader(this).getLoaderWithTouch();
    }

    override fun onDestroy() {
        super.onDestroy()
        loader = null;
        loaderWithTouch = null;
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

}