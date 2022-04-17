package com.luistorm.melitest.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attribute(
    val id: String = "",
    val name: String = "",
    val valueName: String = ""
) : Parcelable