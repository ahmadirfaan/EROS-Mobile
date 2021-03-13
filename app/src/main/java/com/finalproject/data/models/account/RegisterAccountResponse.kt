package com.finalproject.data.models.account

import com.google.gson.annotations.SerializedName

data class RegisterAccountResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: RegisterAccountRequest? = null,

	@field:SerializedName("message")
	val message: String? = null
)

