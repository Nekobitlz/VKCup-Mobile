package com.nekobitlz.photohandler.features.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nekobitlz.photohandler.R
import com.nekobitlz.photohandler.data.models.Album
import com.nekobitlz.photohandler.features.photos.PhotosFragment
import kotlinx.android.synthetic.main.fragment_albums.*

class AlbumsFragment : Fragment() {

    private val adapter by lazy {
        AlbumsAdapter(onClick)
    }

    private val onClick: (Album) -> Unit = {
        openPhotosFragment(it)
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

        rv_albums.adapter = adapter
        rv_albums.setHasFixedSize(true)
        rv_albums.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel = ViewModelProvider(this, albumsViewModelFactory).get(AlbumsViewModel::class.java)
        viewModel.albums.observe(viewLifecycleOwner, Observer {
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