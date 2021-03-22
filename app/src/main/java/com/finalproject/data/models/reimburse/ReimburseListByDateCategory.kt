package com.finalproject.data.models.reimburse

import com.google.gson.annotations.SerializedName

data class ReimburseListByDateCategory(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("employeeId")
	val employeeId: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("categoryId")
	val categoryId: String? = null
)
