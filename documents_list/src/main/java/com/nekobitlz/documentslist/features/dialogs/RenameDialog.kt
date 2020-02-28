package com.nekobitlz.documentslist.features.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.nekobitlz.documentslist.R
import com.nekobitlz.documentslist.data.models.VKDocument
import kotlinx.android.synthetic.main.dialog_rename.view.*

class RenameDialog : DialogFragment() {

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
        val layout = requireActivity().layoutInflater.inflate(R.layout.dialog_rename, null)
        val item = requireArguments().getSerializable(DOCUMENT_KEY) as VKDocument

        return AlertDialog
            .Builder(requireContext())
            .setMessage(resources.getString(R.string.dialog_rename_message))
            .setView(layout)
            .setPositiveButton(R.string.dialog_rename_confirm) { _, _ ->
                listener.onRenameClicked(item, layout.et_new_name.text.toString())
            }
            .setNegativeButton(R.string.dialog_cancel) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }

    companion object {

        private const val DOCUMENT_KEY = "DOCUMENT_KEY"

        fun newInstance(item: VKDocument): RenameDialog {
            val args = Bundle()
            args.putSerializable(DOCUMENT_KEY, item)
            val fragment = RenameDialog()
            fragment.arguments = args
            return fragment
        }
    }
}