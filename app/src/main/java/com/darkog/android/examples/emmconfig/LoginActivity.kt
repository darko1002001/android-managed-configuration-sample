package com.darkog.android.examples.emmconfig

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.darkog.android.examples.emmconfig.R.layout.activity_login
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var configurationManager: AppManagedConfigurationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignIn.setOnClickListener { signIn() }
        configurationManager = AppManagedConfigurationManager(this)
        configurationManager.updateValues()
        if (configurationManager.isDeviceManaged()) {
            email.setText(configurationManager.username())
        }
    }

    private fun signIn() {
        navigateToSimpleActivity()
    }

    private fun navigateToSimpleActivity() {
        val intent = Intent(this, SimpleActivity::class.java)
        startActivity(intent)
        finish()
    }

}