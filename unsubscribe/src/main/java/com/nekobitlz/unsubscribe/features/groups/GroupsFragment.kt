package com.nekobitlz.unsubscribe.features.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nekobitlz.unsubscribe.R
import com.nekobitlz.unsubscribe.data.models.Group
import com.nekobitlz.unsubscribe.features.groupinfo.GroupInfoFragment
import kotlinx.android.synthetic.main.fragment_groups.*

class GroupsFragment : Fragment() {

    private val adapter: GroupsAdapter by lazy {
        GroupsAdapter(onClick)
    }

    private lateinit var viewModel: GroupsViewModel
    private val groupsViewModelFactory by lazy {
        GroupsViewModelFactory()
    }

    private var onClick: (Group, ClickType) -> Unit = { group, clickType ->
        when (clickType) {
            ClickType.SHORT -> {
                showGroupInfoDialog(group)
            }
            ClickType.LONG -> {
                viewModel.onLongClicked()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_groups, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        rv_groups.layoutManager = GridLayoutManager(requireContext(), 3)
        rv_groups.setHasFixedSize(true)
        rv_groups.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, groupsViewModelFactory)
            .get(GroupsViewModel::class.java)

        viewModel.getGroupList().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.isLongTapMode().observe(viewLifecycleOwner, Observer {
            adapter.isLongTapMode = it
            adapter.notifyDataSetChanged()

            if (it) {
                ll_unsubscribe_hint.visibility = View.GONE
                btn_unsubscribe.visibility = View.VISIBLE
            } else {
                ll_unsubscribe_hint.visibility = View.VISIBLE
                btn_unsubscribe.visibility = View.GONE
            }
        })
    }

    private fun showGroupInfoDialog(group: Group) {
        val fragment = GroupInfoFragment.newInstance(group)
        fragment.show(requireFragmentManager(), GROUP_INFO_TAG)
    }

    companion object {
        private const val LONG_TAP_MODE = "LONG_TAP_MODE"
        private const val GROUP_INFO_TAG = "GROUP_INFO_TAG"
    }
}