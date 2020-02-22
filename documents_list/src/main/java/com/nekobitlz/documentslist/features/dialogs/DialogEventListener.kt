package com.nekobitlz.documentslist.features.dialogs

import com.nekobitlz.documentslist.data.VKDocument

interface DialogEventListener {
    fun onRenameClicked(document: VKDocument, text: String)
    fun onDeleteClicked(document: VKDocument)
}