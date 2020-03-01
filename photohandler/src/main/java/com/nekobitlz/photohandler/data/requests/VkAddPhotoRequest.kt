package com.nekobitlz.photohandler.data.requests

import android.net.Uri
import com.nekobitlz.photohandler.data.models.VKFileUploadInfo
import com.nekobitlz.photohandler.data.models.VKSaveInfo
import com.nekobitlz.photohandler.data.models.VKServerUploadInfo
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKHttpPostCall
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class VkAddPhotoRequest(private val albumId: Int, val photo: Uri) : ApiCommand<Int>() {

    override fun onExecute(manager: VKApiManager): Int {
        val uploadInfo = getServerUploadInfo(manager)
        return uploadPhoto(photo, uploadInfo, manager).length
    }

    private fun getServerUploadInfo(manager: VKApiManager): VKServerUploadInfo {
        val uploadInfoCall = VKMethodCall.Builder()
            .method("photos.getUploadServer")
            .args("album_id", albumId)
            .version(manager.config.version)
            .build()

        return manager.execute(uploadInfoCall, ServerUploadInfoParser())
    }

    private fun uploadPhoto(
        uri: Uri,
        serverUploadInfo: VKServerUploadInfo,
        manager: VKApiManager
    ): String {
        val fileUploadCall = VKHttpPostCall.Builder()
            .url(serverUploadInfo.uploadUrl)
            .args("photos_list", uri, "image.jpg")
            .timeout(TimeUnit.MINUTES.toMillis(5))
            .retryCount(RETRY_COUNT)
            .build()
        val fileUploadInfo = manager.execute(fileUploadCall, null, FileUploadInfoParser())

        val saveCall = VKMethodCall.Builder()
            .method("photos.save")
            .args("album_id", albumId)
            .args("server", fileUploadInfo.server)
            .args("photos_list", fileUploadInfo.photo)
            .args("hash", fileUploadInfo.hash)
            .version(manager.config.version)
            .build()

        val saveInfo = manager.execute(saveCall, SaveInfoParser())

        return saveInfo.getAttachment()
    }

    companion object {
        const val RETRY_COUNT = 3
    }

    private class ServerUploadInfoParser : VKApiResponseParser<VKServerUploadInfo> {
        override fun parse(response: String): VKServerUploadInfo {
            try {
                val joResponse = JSONObject(response).getJSONObject("response")
                return VKServerUploadInfo(
                    uploadUrl = joResponse.getString("upload_url"),
                    albumId = joResponse.getInt("album_id"),
                    userId = joResponse.getInt("user_id")
                )
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    private class FileUploadInfoParser : VKApiResponseParser<VKFileUploadInfo> {
        override fun parse(response: String): VKFileUploadInfo {
            try {
                val joResponse = JSONObject(response)
                return VKFileUploadInfo(
                    server = joResponse.getString("server"),
                    photo = joResponse.getJSONArray("photos_list").toString(),
                    hash = joResponse.getString("hash")
                )
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }

    private class SaveInfoParser : VKApiResponseParser<VKSaveInfo> {
        override fun parse(response: String): VKSaveInfo {
            try {
                val joResponse = JSONObject(response).getJSONArray("response").getJSONObject(0)
                return VKSaveInfo(
                    id = joResponse.getInt("id"),
                    albumId = joResponse.getInt("album_id"),
                    ownerId = joResponse.getInt("owner_id")
                )
            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }
}