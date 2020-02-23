package com.nekobitlz.documentslist.features.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.nekobitlz.documentslist.R
import com.nekobitlz.documentslist.data.models.VKDocument

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
        val input = EditText(requireContext())
        input.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        val item = requireArguments().getSerializable(DOCUMENT_KEY) as VKDocument

        return AlertDialog
            .Builder(requireContext())
            .setMessage(resources.getString(R.string.dialog_rename_message))
            .setView(input)
            .setPositiveButton(R.string.dialog_rename_confirm) { _, _ ->
                listener.onRenameClicked(item, input.text.toString())
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