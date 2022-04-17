package com.luistorm.melitest.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Seller(
    val id: String = "",
    val powerSellerStatus: String = ""
) : Parcelable
