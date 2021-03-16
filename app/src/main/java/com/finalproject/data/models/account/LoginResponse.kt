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

	@field:SerializedName("role")
	val role: Role? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class Role(

	@field:SerializedName("roleName")
	val roleName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
