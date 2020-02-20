package com.nekobitlz.documentslist.features.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nekobitlz.documentslist.R
import com.nekobitlz.documentslist.features.DocumentsFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

class AuthFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        VK.login(requireActivity(), arrayListOf(VKScope.DOCS))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, DocumentsFragment())
                    .commit()
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_LONG).show()
            }
        }

        if (!VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}