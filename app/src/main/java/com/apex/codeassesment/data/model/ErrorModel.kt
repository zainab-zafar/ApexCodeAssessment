package com.apex.codeassesment.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class ErrorModel(
    @SerializedName("message") var message: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("trace") var trace: String? = null

) : Parcelable
