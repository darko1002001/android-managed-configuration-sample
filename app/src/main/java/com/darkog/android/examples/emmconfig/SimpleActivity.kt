package com.darkog.android.examples.emmconfig

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import kotlinx.android.synthetic.main.activity_simple.*
import android.content.Intent
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.content.Context


class SimpleActivity : AppCompatActivity() {

    private lateinit var appManagedConfigurationManager: AppManagedConfigurationManager
    private lateinit var counterModifier: CounterModifier


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)

        counterModifier = CounterModifier(buttonIncrement)
        appManagedConfigurationManager = AppManagedConfigurationManager(this)

    }

    private var broadcastReceiver: BroadcastReceiver? = null

    public override fun onStart() {
        super.onStart()
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // Fetch and use new values
            }
        }
        this.registerReceiver(broadcastReceiver,
                IntentFilter(Intent.ACTION_APPLICATION_RESTRICTIONS_CHANGED))
    }

    public override fun onStop() {
        super.onStop()
        if (broadcastReceiver != null) {
            this.unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
        }
    }

    override fun onResume() {
        super.onResume()
        updateConfiguredValues()

    }

    private fun updateConfiguredValues() {
        appManagedConfigurationManager.updateValues()
        val isManaged = appManagedConfigurationManager.isDeviceManaged()
        val isMissingConfig = appManagedConfigurationManager.isMissingConfigurations()


        buttonIncrement.isEnabled = appManagedConfigurationManager.canIncrementNumber()
        counterModifier.counter = appManagedConfigurationManager.number()

        textViewWelcomeMessage.text = appManagedConfigurationManager.welcomeMessage()
        textViewColorSelection.text = appManagedConfigurationManager.color()
        textViewUserLevel.text = TextUtils.join("\n", appManagedConfigurationManager.userLevel())
        textViewSecretCode.text = getString(R.string.text_secret_code, appManagedConfigurationManager.secretCode())
    }

    private class CounterModifier(val button: Button) {
        var counter: Int = 0
            set(value) {
                field = value
                updateCount()
            }

        init {
            button.setOnClickListener {
                counter++
            }
            updateCount()
        }

        @SuppressLint("StringFormatMatches")
        private fun updateCount() {
            button.text = button.context.getString(R.string.counter_value, this.counter)
        }
    }
}
