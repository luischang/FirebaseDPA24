package com.example.firebasedpa24.model

import com.google.gson.annotations.SerializedName

data class SpriteModel(
    @SerializedName("back_default")
    val backDefault: String,
    @SerializedName("front_default")
    val frontDefault: String
)
