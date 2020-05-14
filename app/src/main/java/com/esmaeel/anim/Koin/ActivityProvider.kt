package com.esmaeel.anim.Koin

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityProvider(application: Application) {

    var activeActivity: Activity? = null

    init {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {

            override fun onActivityPaused(activity: Activity?) {
                activeActivity = null
            }

            override fun onActivityStarted(activity: Activity) {
                activeActivity = activity
            }

            override fun onActivityDestroyed(activity: Activity) {
                activeActivity = null
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityStopped(activity: Activity) {
                activeActivity = null
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activeActivity = activity
            }

            override fun onActivityResumed(activity: Activity?) {
                activeActivity = activity
            }

        })
    }

}