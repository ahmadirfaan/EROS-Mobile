package com.finalproject.data.models.reimburse

import com.google.gson.annotations.SerializedName

data class BillResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("billImage")
	val billImage: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
