package com.nekobitlz.photohandler.features.photos

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
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
import com.nekobitlz.photohandler.data.models.Photo
import com.nekobitlz.photohandler.utils.PathUtils
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_photos.view.*

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

        initToolbar()
        initViews()
        initViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQ_CODE) {
            if (resultCode == RESULT_OK && data != null && data.data != null) {
                addPhoto(Uri.parse(PathUtils.getPath(requireContext(), data.data!!)))
            }
        }
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

    private fun initToolbar() {
        requireActivity().setActionBar(toolbar)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        requireActivity().actionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
        toolbar.title = ""

        toolbar.toolbar_btn_add.setOnClickListener {
            requestPhoto()
        }
    }

    private fun requestPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQ_CODE)
    }

    private fun addPhoto(photo: Uri?) {
        if (photo != null) {
            viewModel.addPhoto(photo)
            viewModel.requestResult.observe(viewLifecycleOwner, Observer {
                if (it) {
                    Toast.makeText(requireContext(), R.string.toast_success, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), R.string.toast_error, Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(requireContext(), getString(R.string.toast_error_no_photo), Toast.LENGTH_SHORT).show()
        }
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
        private const val IMAGE_REQ_CODE = 101

        fun getInstance(album: Album): PhotosFragment {
            val bundle = Bundle()
            bundle.putSerializable(ALBUM_TAG, album)
            val fragment = PhotosFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}