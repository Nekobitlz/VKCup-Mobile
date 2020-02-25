package com.nekobitlz.unsubscribe.features.groupinfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nekobitlz.unsubscribe.R
import com.nekobitlz.unsubscribe.data.models.Group
import kotlinx.android.synthetic.main.dialog_group_info.*

class GroupInfoFragment : BottomSheetDialogFragment() {

    private lateinit var group: Group

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        group = requireArguments().getSerializable(GROUP_TAG) as Group
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_group_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_title.text = group.name
        tv_followers.text = group.getFollowers(resources)
        tv_last_post.text = group.getLastPost(resources)

        if (group.description.isBlank()) {
            tv_description.text = resources.getString(R.string.group_no_description)
        } else {
            tv_description.text = group.description
        }

        btn_dismiss.setOnClickListener {
            dismiss()
        }

        btn_open.setOnClickListener {
            openGroup(group.screenName)
        }
    }

    private fun openGroup(screenName: String) {
        val url = Uri.parse("https://vk.com/$screenName")
        val intent = Intent(Intent.ACTION_VIEW, url)
        startActivity(intent)
    }

    companion object {
        private const val GROUP_TAG = "GROUP_TAG"

        fun newInstance(group: Group): GroupInfoFragment {
            val bundle = Bundle()
            bundle.putSerializable(GROUP_TAG, group)

            val fragment = GroupInfoFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}