package com.nekobitlz.documentslist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nekobitlz.documentslist.features.DocumentsFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

class DocumentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documents)

        checkLoginState()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                saveToken(token)
                openDocumentsFragment(token)
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(baseContext, "Login Failed", Toast.LENGTH_LONG).show()
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
        VK.login(this, arrayListOf(VKScope.DOCS, VKScope.OFFLINE))
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
        val fragment = DocumentsFragment()
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
