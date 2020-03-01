package com.nekobitlz.documentslist.features.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.nekobitlz.documentslist.R
import com.nekobitlz.documentslist.data.models.VKDocument
import java.lang.ClassCastException

class DeleteDialog : DialogFragment() {

    private lateinit var listener: DialogEventListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = targetFragment as DialogEventListener
        } catch (e: ClassCastException) {
            throw ClassCastException(parentFragment.toString() + " must implement DialogEventListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val item = requireArguments().getSerializable(DOCUMENT_KEY) as VKDocument

        return AlertDialog
            .Builder(requireContext())
            .setMessage("${resources.getString(R.string.dialog_delete_message)} ${item.title}?")
            .setPositiveButton(R.string.dialog_delete_confirm) { _, _ ->
                listener.onDeleteClicked(item)
            }
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }

    companion object {

        private const val DOCUMENT_KEY = "DOCUMENT_KEY"

        fun newInstance(item: VKDocument): DeleteDialog {
            val args = Bundle()
            args.putSerializable(DOCUMENT_KEY, item)
            val fragment = DeleteDialog()
            fragment.arguments = args
            return fragment
        }
    }
}