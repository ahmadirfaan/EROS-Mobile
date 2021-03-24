package com.finalproject.data.models.reimburse

import android.os.Parcelable
import com.finalproject.data.models.account.EmployeeResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class ReimbursementList(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<ReimbursementResponse?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class ReimbursementResponse(

	@field:SerializedName("dateOfClaimSubmission")
	val dateOfClaimSubmission: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("statusOnHc")
	val statusOnHc: Boolean? = null,

	@field:SerializedName("employeeId")
	val employeeId: EmployeeResponse? = null,

	@field:SerializedName("statusSuccess")
	val statusSuccess: Boolean? = null,

	@field:SerializedName("borneCost")
	val borneCost: Int? = null,

	@field:SerializedName("disbursementDate")
	val disbursementDate: String? = null,

	@field:SerializedName("statusReject")
	val statusReject: Boolean? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("statusOnFinance")
	val statusOnFinance: Boolean? = null,

	@field:SerializedName("categoryId")
	val categoryId: @RawValue CategoryId? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("claimFee")
	val claimFee: Int? = null
) : Parcelable

@Parcelize
data class CategoryId(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("categoryName")
	val categoryName: String? = null
) : Parcelable