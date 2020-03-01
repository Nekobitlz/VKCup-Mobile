package com.nekobitlz.documentslist.data.models

enum class VkDocumentType(val intentType: String) {
    TEXT("text/*"),
    ARCHIVE("application/zip"),
    GIF("image/gif"),
    IMAGE("image/*"),
    AUDIO("audio/x-wav"),
    VIDEO("video/*"),
    BOOK("application/epub+zip"),
    OTHERS("*/*");

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