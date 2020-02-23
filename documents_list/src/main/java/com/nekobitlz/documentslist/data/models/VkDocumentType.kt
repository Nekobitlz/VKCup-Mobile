package com.nekobitlz.documentslist.data.models

enum class VkDocumentType(val typeId: Int, val intentType: String) {
    TEXT(1, "text/*"),
    ARCHIVE(2, "application/zip"),
    GIF(3, "image/gif"),
    IMAGE(4, "image/*"),
    AUDIO(5, "audio/x-wav"),
    VIDEO(6, "video/*"),
    BOOK(7, "application/epub+zip"),
    OTHERS(8, "*/*");

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