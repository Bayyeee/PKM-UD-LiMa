package com.project.pkm_ud_lima.data.response

import com.google.gson.annotations.SerializedName

data class FlameResponse(

	@field:SerializedName("jarak_api")
	val jarakApi: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("waktu_input")
	val waktuInput: String? = null,

	@field:SerializedName("value_api")
	val valueApi: Int? = null
)
