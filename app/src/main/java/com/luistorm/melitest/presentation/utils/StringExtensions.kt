package com.luistorm.melitest.presentation.utils

private const val HTTP = "http://"
private const val HTTPS = "https://"

fun String.transformToHttps() = this.replace(HTTP, HTTPS)