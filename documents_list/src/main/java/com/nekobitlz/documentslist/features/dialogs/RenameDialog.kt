package com.nekobitlz.documentslist.features.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.marginRight
import androidx.fragment.app.DialogFragment
import com.nekobitlz.documentslist.R
import com.nekobitlz.documentslist.data.VKDocument

class RenameDialog(private val item: VKDocument) : DialogFragment() {

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
}