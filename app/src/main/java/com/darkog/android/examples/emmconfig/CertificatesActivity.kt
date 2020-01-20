package com.darkog.android.examples.emmconfig

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import kotlinx.android.synthetic.main.activity_keystore.*
import java.security.KeyStore

class CertificatesActivity : AppCompatActivity() {

    val TAG = CertificatesActivity::class.simpleName

    private lateinit var configurationManager: AppManagedConfigurationManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keystore)

        configurationManager = AppManagedConfigurationManager(this)
        configurationManager.updateValues()

        val alias = configurationManager.certificateAlias()

        val ks: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        Log.d(TAG, "Keystore size ${ks.size()}")
        // val aliases = ks.aliases().toList()
        // val entry: KeyStore.Entry = ks.getEntry(alias, null)

        // textViewCertificateInfo.text = TextUtils.join(", ", aliases)
    }
}
