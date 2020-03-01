package com.nekobitlz.photohandler.features.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nekobitlz.photohandler.R
import com.nekobitlz.photohandler.data.models.Album
import com.nekobitlz.photohandler.features.photos.PhotosFragment
import kotlinx.android.synthetic.main.fragment_albums.*
import kotlinx.android.synthetic.main.fragment_albums.view.*

class AlbumsFragment : Fragment() {

    private val adapter by lazy {
        AlbumsAdapter(onClick)
    }

    private val onClick: (Album, ClickType) -> Unit = { album, type ->
        when (type) {
            ClickType.SHORT -> if (adapter.isEditableMode) {
                viewModel.onRemoveClicked(album)
                observeResult()
            } else {
                openPhotosFragment(album)
            }
            ClickType.LONG -> {
                viewModel.onEditClicked()
                observeEditableMode()
            }
        }
    }

    private lateinit var viewModel: AlbumsViewModel
    private val albumsViewModelFactory by lazy {
        AlbumsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_albums, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initViewModel()
        initToolbar()
    }

    private fun initRecyclerView() {
        rv_albums.adapter = adapter
        rv_albums.setHasFixedSize(true)
        rv_albums.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun initToolbar() {
        toolbar.toolbar_btn_add.setOnClickListener {
            viewModel.onAddClicked()
            observeResult()
        }

        toolbar.toolbar_btn_edit.setOnClickListener {
            viewModel.onEditClicked()
            observeEditableMode()
        }

        toolbar.toolbar_btn_close.setOnClickListener {
            viewModel.onCloseClicked()
            observeEditableMode()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, albumsViewModelFactory).get(AlbumsViewModel::class.java)
        observeAlbums()
        observeEditableMode()
    }

    private fun observeEditableMode() {
        viewModel.isEditableMode().observe(viewLifecycleOwner, Observer {
            if (it) {
                adapter.isEditableMode = true
                adapter.notifyDataSetChanged()

                toolbar.toolbar_btn_add.visibility = View.GONE
                toolbar.toolbar_btn_edit.visibility = View.GONE
                toolbar.toolbar_btn_close.visibility = View.VISIBLE
                toolbar.tv_title.text = resources.getString(R.string.editable_mode)
            } else {
                adapter.isEditableMode = false
                adapter.notifyDataSetChanged()

                toolbar.toolbar_btn_add.visibility = View.VISIBLE
                toolbar.toolbar_btn_edit.visibility = View.VISIBLE
                toolbar.toolbar_btn_close.visibility = View.GONE
                toolbar.tv_title.text = resources.getString(R.string.app_name)
            }
        })
    }

    private fun observeResult() {
        viewModel.requestResult.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(requireContext(), R.string.toast_success, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), R.string.toast_error, Toast.LENGTH_SHORT)
                    .show()
            }
        })
        observeAlbums()
    }

    private fun observeAlbums() {
        viewModel.getAlbums().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun openPhotosFragment(album: Album) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, PhotosFragment.getInstance(album))
            .addToBackStack(null)
            .commit()
    }
}