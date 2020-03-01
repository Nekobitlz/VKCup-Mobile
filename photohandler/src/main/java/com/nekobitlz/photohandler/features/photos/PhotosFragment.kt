package com.nekobitlz.photohandler.features.photos

import android.content.Intent
import android.net.Uri
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
import com.nekobitlz.photohandler.data.models.Photo
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : Fragment() {

    private lateinit var album: Album

    private val adapter by lazy {
        PhotosAdapter(onClick)
    }

    private val onClick: (Photo) -> Unit = {
        openPhoto(it)
    }

    private lateinit var viewModel: PhotosViewModel
    private val photosViewModelFactory by lazy {
        PhotosViewModelFactory(album)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        album = requireArguments().getSerializable(ALBUM_TAG) as Album
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_photos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        rv_photos.adapter = adapter
        rv_photos.setHasFixedSize(true)
        rv_photos.layoutManager = GridLayoutManager(requireContext(), 3)

        tv_photos_title.text = album.name
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, photosViewModelFactory).get(PhotosViewModel::class.java)
        viewModel.photos.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun openPhoto(photo: Photo) {
        val uri = Uri.parse(photo.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        intent.setDataAndType(uri, "image/*")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
    }

    companion object {
        private const val ALBUM_TAG = "ALBUM_TAG"

        fun getInstance(album: Album): PhotosFragment {
            val bundle = Bundle()
            bundle.putSerializable(ALBUM_TAG, album)
            val fragment = PhotosFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}