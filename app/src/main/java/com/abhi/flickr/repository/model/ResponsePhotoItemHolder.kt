package com.abhi.flickr.repository.model

import com.abhi.flickr.repository.entities.FlikrEntity
import java.util.*

data class PhotoResult(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: String,
    val photo: ArrayList<FlikrEntity>
)

data class ResponsePhotoItemHolder(
    val stat: String,
    val photos: PhotoResult)
