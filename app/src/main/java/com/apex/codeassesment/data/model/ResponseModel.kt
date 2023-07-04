package com.apex.codeassesment.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ResponseModel(
    @SerializedName("results") var results: ArrayList<User>? = null,
    @SerializedName("success") var isSuccess: Boolean = false,
    @SerializedName("loading") var isLoading: Boolean = false,
    @SerializedName("error") var error: ErrorModel? = null,

    ) : Parcelable