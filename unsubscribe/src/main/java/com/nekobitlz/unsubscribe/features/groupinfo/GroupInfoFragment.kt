package com.nekobitlz.unsubscribe.features.groupinfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nekobitlz.unsubscribe.R
import com.nekobitlz.unsubscribe.data.models.Group
import com.nekobitlz.unsubscribe.di.injector
import com.nekobitlz.unsubscribe.features.groupinfo.di.GroupInfoComponent
import kotlinx.android.synthetic.main.dialog_group_info.*

class GroupInfoFragment : BottomSheetDialogFragment() {

    private lateinit var group: Group
    private lateinit var viewModel: GroupInfoViewModel
    private lateinit var component: GroupInfoComponent

    private val viewModelFactory: GroupInfoViewModelFactory by lazy {
        component.groupInfoViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        group = requireArguments().getSerializable(GROUP_TAG) as Group
        component = injector.getGroupInfoModule(group)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_group_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViews() {
        tv_title.text = group.name

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

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(GroupInfoViewModel::class.java)

        viewModel.friendsCount.observe(viewLifecycleOwner, Observer {
            tv_followers.text = group.getFollowers(resources)
        })

        viewModel.lastPostDate.observe(viewLifecycleOwner, Observer {
            tv_last_post.text = group.getLastPost(resources)
        })
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