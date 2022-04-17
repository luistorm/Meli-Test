package com.luistorm.melitest.presentation.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


fun View.hideKeyboard() {
    val imm: InputMethodManager =
        this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}