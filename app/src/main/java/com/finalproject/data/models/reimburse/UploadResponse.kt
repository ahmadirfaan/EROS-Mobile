package com.finalproject.data.models.reimburse

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("message")
	val message: String? = null
)
