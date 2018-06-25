package com.darkog.android.examples.emmconfig

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonSignIn.setOnClickListener { signIn() }
    }

    private fun signIn() {
        Single.just(true)
                .compose(applyProgressTransformer())
                .observeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _ -> navigateToSimpleActivity() })
    }

    private fun navigateToSimpleActivity() {
        val intent = Intent(this, SimpleActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        loginForm.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun <T> applyProgressTransformer(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.observeOn(AndroidSchedulers.mainThread())
                    .doOnError { showProgress(false) }
                    .doOnSubscribe { showProgress(true) }
        }

    }
}
