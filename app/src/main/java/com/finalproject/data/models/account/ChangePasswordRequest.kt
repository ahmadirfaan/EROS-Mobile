package com.finalproject.data.models.account

import com.google.gson.annotations.SerializedName


data class ChangePasswordRequest(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("idLogin")
	val idLogin: String? = null
)
