package com.darkog.android.examples.emmconfig

import android.os.Build



class AppManagedConfigurationManager {

    private val BUNDLE_SUPPORTED = Build.VERSION.SDK_INT >= 23

    private val KEY_HOMEPAGE_URL = "homepageUrl"
    private val KEY_HTTPS_ONLY = "useHttpsOnly"

}