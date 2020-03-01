package com.nekobitlz.vksharing.features.welcome

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nekobitlz.vksharing.R
import com.nekobitlz.vksharing.SharingActivity.Companion.USER_ID
import com.nekobitlz.vksharing.features.share.ShareFragment
import com.nekobitlz.vksharing.utils.PathUtils
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {

    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt(USER_ID)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_welcome, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_choose_photo.setOnClickListener {
            requestPhoto()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQ_CODE) {
            if (resultCode == RESULT_OK && data != null && data.data != null) {
                sharePost(Uri.parse(PathUtils.getPath(requireContext(), data.data!!)))
            } else {
                sharePost()
            }
        }
    }

    private fun requestPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQ_CODE)
    }

    private fun sharePost(uri: Uri? = null) {
        val fragment = ShareFragment.newInstance(userId, uri)
        fragment.show(requireFragmentManager(), SHARE_TAG)
    }

    companion object {
        private const val SHARE_TAG = "SHARE_TAG"
        private const val IMAGE_REQ_CODE = 101
    }
}