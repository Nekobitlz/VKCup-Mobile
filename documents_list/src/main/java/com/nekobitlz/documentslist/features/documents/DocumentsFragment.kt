package com.nekobitlz.documentslist.features.documents

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekobitlz.documentslist.DocumentsActivity.Companion.USER_ID
import com.nekobitlz.documentslist.R
import com.nekobitlz.documentslist.data.models.VKDocument
import com.nekobitlz.documentslist.features.dialogs.DeleteDialog
import com.nekobitlz.documentslist.features.dialogs.DialogEventListener
import com.nekobitlz.documentslist.features.dialogs.RenameDialog
import kotlinx.android.synthetic.main.fragment_documents.*
import android.net.Uri
import com.nekobitlz.documentslist.features.dialogs.ClickType

class DocumentsFragment : Fragment(), DialogEventListener {

    private val documentsViewModelFactory: DocumentsViewModelFactory by lazy {
        DocumentsViewModelFactory(userId)
    }

    private val onClick: (VKDocument, ClickType) -> Boolean = { document, type ->
        when (type) {
            ClickType.OPEN -> openDocument(document)
            ClickType.RENAME -> showRenameDialog(document)
            ClickType.DELETE -> showDeleteDialog(document)
        }
        true
    }

    private var userId: Int = 0
    private val adapter: DocumentsAdapter =
        DocumentsAdapter(onClick)
    private lateinit var viewModel: DocumentsViewModel

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

    override fun onRenameClicked(document: VKDocument, text: String) {
        viewModel.onRenameClicked(document, text)
        observeDialog(R.string.dialog_rename_success)
        observeDocuments()
    }

    override fun onDeleteClicked(document: VKDocument) {
        viewModel.onDeleteClicked(document)
        observeDialog(R.string.dialog_delete_success)
        observeDocuments()
    }

    private fun observeDialog(dialogMessage: Int) {
        viewModel.dialogEvent.observe(viewLifecycleOwner, Observer {
            if (it == 1) {
                Toast.makeText(requireContext(), dialogMessage, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), R.string.dialog_error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        rv_documents.layoutManager = LinearLayoutManager(context)
        rv_documents.setHasFixedSize(true)
        rv_documents.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, documentsViewModelFactory)
            .get(DocumentsViewModel::class.java)

        swipe_refresh.setOnRefreshListener {
            observeDocuments()
        }

        observeDocuments()
    }

    private fun observeDocuments() {
        swipe_refresh.isRefreshing = true
        viewModel.getDocuments().observe(viewLifecycleOwner, Observer {
            swipe_refresh.isRefreshing = false
            adapter.submitList(it)
        })
    }

    private fun showRenameDialog(item: VKDocument) {
        val fragmentManager = requireActivity().supportFragmentManager
        RenameDialog.newInstance(item).also {
            it.setTargetFragment(this@DocumentsFragment, 0)
            it.show(fragmentManager,
                RENAME_DIALOG
            )
        }
    }

    private fun showDeleteDialog(item: VKDocument) {
        val fragmentManager = requireActivity().supportFragmentManager
        DeleteDialog.newInstance(item).also {
            it.setTargetFragment(this@DocumentsFragment, 0)
            it.show(fragmentManager,
                DELETE_DIALOG
            )
        }
    }

    private fun openDocument(item: VKDocument) {
        val uri = Uri.parse(item.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        intent.setDataAndType(uri, item.getType().intentType)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
    }

    companion object {
        private const val RENAME_DIALOG = "RENAME_DIALOG"
        private const val DELETE_DIALOG = "DELETE_DIALOG"
    }
}