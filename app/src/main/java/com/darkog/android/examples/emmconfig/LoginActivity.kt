package com.darkog.android.examples.emmconfig

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var configurationManager: AppManagedConfigurationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonGoToSimple.setOnClickListener { goToSimple() }
        buttonGoToCertificates.setOnClickListener { goToCertificates() }
        configurationManager = AppManagedConfigurationManager(this)
        configurationManager.updateValues()
        if (configurationManager.isDeviceManaged()) {
            email.setText(configurationManager.username())
        }
    }

    private fun goToSimple() {
        val intent = Intent(this, SimpleActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToCertificates() {
        val intent = Intent(this, CertificatesActivity::class.java)
        startActivity(intent)
        finish()
    }

}