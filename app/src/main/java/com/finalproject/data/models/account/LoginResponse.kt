package com.finalproject.data.models.account

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("roleId")
	val roleId: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
