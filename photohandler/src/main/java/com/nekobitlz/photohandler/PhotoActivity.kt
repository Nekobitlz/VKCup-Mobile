package com.nekobitlz.photohandler

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nekobitlz.photohandler.features.albums.AlbumsFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        if (savedInstanceState == null) {
            checkLoginState()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                saveToken(token)
                openDocumentsFragment(token)
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(
                    baseContext,
                    resources.getString(R.string.login_failed),
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }

        if (!VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun checkLoginState() {
        if (VK.isLoggedIn()) {
            val token = restoreToken()

            if (token == null) {
                login()
            } else {
                openDocumentsFragment(token)
            }
        } else {
            login()
        }
    }

    private fun login() {
        VK.login(this, arrayListOf(VKScope.OFFLINE, VKScope.PHOTOS))
    }

    private fun restoreToken(): VKAccessToken? = VKAccessToken.restore(
        applicationContext.getSharedPreferences(
            SHARED_PREFS_NAME, Context.MODE_PRIVATE
        )
    )

    private fun saveToken(token: VKAccessToken) {
        token.save(
            applicationContext.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        )
    }

    private fun openDocumentsFragment(token: VKAccessToken) {
        val fragment = AlbumsFragment()
        fragment.arguments = Bundle().apply { putInt(USER_ID, token.userId) }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }

    companion object {
        private const val SHARED_PREFS_NAME = "SHARED_PREFS_NAME"
        const val USER_ID = "USER_ID"
    }
}