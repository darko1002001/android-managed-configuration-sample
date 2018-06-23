package com.darkog.android.examples.emmconfig

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import com.darkog.android.examples.emmconfig.R.layout.activity_simple
import kotlinx.android.synthetic.main.activity_simple.*

class SimpleActivity : AppCompatActivity() {

    private lateinit var appManagedConfigurationManager: AppManagedConfigurationManager
    private lateinit var counterModifier: CounterModifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_simple)

        appManagedConfigurationManager = AppManagedConfigurationManager(this)

        counterModifier = CounterModifier(buttonIncrement)
        updateConfiguredValues()
    }

    private fun updateConfiguredValues() {
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
