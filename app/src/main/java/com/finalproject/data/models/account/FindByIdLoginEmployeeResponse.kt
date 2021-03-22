package com.finalproject.data.models.account

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class FindByIdLoginEmployeeResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: EmployeeResponse? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class EmployeeResponse(

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("accountName")
	val accountName: String? = null,

	@field:SerializedName("ktpAddress")
	val ktpAddress: String? = null,

	@field:SerializedName("bloodType")
	val bloodType: String? = null,

	@field:SerializedName("employeeStatus")
	val employeeStatus: String? = null,

	@field:SerializedName("npwpAddress")
	val npwpAddress: String? = null,

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("joinDate")
	val joinDate: String? = null,

	@field:SerializedName("nip")
	val nip: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("placeOfBirth")
	val placeOfBirth: String? = null,

	@field:SerializedName("npwp")
	val npwp: String? = null,

	@field:SerializedName("dateOfBirth")
	val dateOfBirth: String? = null,

	@field:SerializedName("emergencyNumber")
	val emergencyNumber: String? = null,

	@field:SerializedName("accountNumber")
	val accountNumber: String? = null,

	@field:SerializedName("religion")
	val religion: String? = null,

	@field:SerializedName("verifiedHc")
	val verifiedHc: Boolean? = null,

	@field:SerializedName("employeeType")
	val employeeType: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("numberOfChildren")
	val numberOfChildren: Int? = null,

	@field:SerializedName("biologicalMothersName")
	val biologicalMothersName: String? = null,

	@field:SerializedName("postalCodeOfIdCard")
	val postalCodeOfIdCard: String? = null,

	@field:SerializedName("idLogin")
	val idLogin: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("spouseName")
	val spouseName: String? = null,

	@field:SerializedName("maritalStatus")
	val maritalStatus: String? = null,

	@field:SerializedName("residenceAddress")
	val residenceAddress: String? = null,

	@field:SerializedName("verifiedEmail")
	val verifiedEmail: Boolean? = null,

) : Parcelable
