package com.finalproject.data.models.reimburse

import com.google.gson.annotations.SerializedName

data class AddReimbursementResponse(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: ReimbursementResponse? = null,

    @field:SerializedName("message")
    val message: String? = null
)
