package com.nekobitlz.vksharing.features.share

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nekobitlz.vksharing.R
import com.nekobitlz.vksharing.SharingActivity.Companion.USER_ID
import kotlinx.android.synthetic.main.bottom_sheet_share.*

class ShareFragment(val userId: Int, val photoUri: Uri? = null) : BottomSheetDialogFragment() {

    /*
        private var photoUri: Uri? = null
        private var userId: Int = 0
    */
    private lateinit var viewModel: ShareViewModel
    private val shareViewModelFactory: ShareViewModelFactory by lazy {
        ShareViewModelFactory()
    }

    private val bottomBehavior
        get() = (dialog as? BottomSheetDialog)?.behavior

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)
/*
        photoUri = savedInstanceState?.getParcelable(URI_TAG)
        userId = savedInstanceState?.getInt(USER_ID)!!
*/
    }

    override fun onStart() {
        super.onStart()
        bottomBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_share, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, shareViewModelFactory).get(ShareViewModel::class.java)
    }

    private fun initViews() {
        iv_photo.clipToOutline = true
        btn_dismiss.clipToOutline = true

        if (photoUri == null) {
            iv_photo.visibility = View.GONE
        } else {
            iv_photo.setImageURI(photoUri)
        }

        btn_dismiss.setOnClickListener {
            this.dismiss()
        }

        btn_send.setOnClickListener {
            val text = et_comment.text.toString()

            if (text.isBlank() && photoUri == null) {
                showToast(resources.getString(R.string.post_empty_error))
            } else {
                viewModel.onSendClicked(userId, photoUri, text)
                observeResult()
            }

        }
    }

    private fun observeResult() {
        viewModel.requestResult.observe(viewLifecycleOwner, Observer {
            if (it > 0) {
                showToast("${resources.getString(R.string.post_success)} $it")
            } else {
                showToast(resources.getString(R.string.post_failed))
            }
        })
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
        this.dismiss()
    }

    companion object {
        private const val URI_TAG = "URI_TAG"

        fun newInstance(userId: Int, uri: Uri?): ShareFragment {
            val bundle = Bundle()
            bundle.putParcelable(URI_TAG, uri)
            bundle.putInt(USER_ID, userId)

            val fragment = ShareFragment(userId, uri)
            fragment.arguments = bundle

            return fragment
        }
    }
}