package com.finalproject.data.models.account

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: ForgotPasswordRequest? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ForgotPasswordRequest(

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
