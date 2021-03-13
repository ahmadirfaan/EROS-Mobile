package com.finalproject.data.models.account

import com.google.gson.annotations.SerializedName

data class RegisterAccountRequest(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
