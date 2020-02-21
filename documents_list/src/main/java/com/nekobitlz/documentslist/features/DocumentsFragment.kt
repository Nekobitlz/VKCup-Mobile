package com.nekobitlz.documentslist.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekobitlz.documentslist.DocumentsActivity.Companion.USER_ID
import com.nekobitlz.documentslist.R
import kotlinx.android.synthetic.main.fragment_documents.*

class DocumentsFragment : Fragment() {

    private var userId: Int = 0
    private val adapter: DocumentsAdapter = DocumentsAdapter()
    private lateinit var viewModel: DocumentsViewModel

    private val documentsViewModelFactory: DocumentsViewModelFactory by lazy {
        DocumentsViewModelFactory(userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = arguments?.getInt(USER_ID)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_documents, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView() {
        rv_documents.layoutManager = LinearLayoutManager(context)
        rv_documents.setHasFixedSize(true)
        rv_documents.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, documentsViewModelFactory)
                .get(DocumentsViewModel::class.java)

        viewModel.documents.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}