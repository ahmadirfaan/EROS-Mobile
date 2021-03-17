package com.finalproject.data.models.account

import com.google.gson.annotations.SerializedName

data class FormAccountRequest(

	@field:SerializedName("placeOfBirth")
	val placeOfBirth: String? = null,

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("accountName")
	val accountName: String? = null,

	@field:SerializedName("ktpAddress")
	val ktpAddress: String? = null,

	@field:SerializedName("npwp")
	val npwp: String? = null,

	@field:SerializedName("dateOfBirth")
	val dateOfBirth: String? = null,

	@field:SerializedName("emergencyNumber")
	val emergencyNumber: String? = null,

	@field:SerializedName("bloodType")
	val bloodType: Int? = null,

	@field:SerializedName("accountNumber")
	val accountNumber: String? = null,

	@field:SerializedName("religion")
	val religion: Int? = null,

	@field:SerializedName("npwpAddress")
	val npwpAddress: String? = null,

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("numberOfChildren")
	val numberOfChildren: Int? = null,

	@field:SerializedName("biologicalMothersName")
	val biologicalMothersName: String? = null,

	@field:SerializedName("postalCodeOfIdCard")
	val postalCodeOfIdCard: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("spouseName")
	val spouseName: String? = null,

	@field:SerializedName("maritalStatus")
	val maritalStatus: Int? = null,

	@field:SerializedName("residenceAddress")
	val residenceAddress: String? = null
)
