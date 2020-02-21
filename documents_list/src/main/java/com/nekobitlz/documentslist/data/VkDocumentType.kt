package com.nekobitlz.documentslist.data

enum class VkDocumentType(typeId: Int) {
    TEXT(1),
    ARCHIVE(2),
    GIF(3),
    IMAGE(4),
    AUDIO(5),
    VIDEO(6),
    BOOK(7),
    OTHERS(8);

    companion object {

        fun createVkDocumentType(typeId: Int): VkDocumentType = when (typeId) {
            1 -> TEXT
            2 -> ARCHIVE
            3 -> GIF
            4 -> IMAGE
            5 -> AUDIO
            6 -> VIDEO
            7 -> BOOK
            else -> OTHERS
        }
    }
}