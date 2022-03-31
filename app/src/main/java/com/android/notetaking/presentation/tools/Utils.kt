package com.android.notetaking.presentation.tools

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat

/**
 * Created by bekjan on 29.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */


fun Context.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun getFormattedCreateDate(time: Long): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm")
    return simpleDateFormat.format(time)
}