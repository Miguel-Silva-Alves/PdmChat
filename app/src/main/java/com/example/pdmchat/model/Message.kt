package com.example.pdmchat.model

import android.os.Parcelable
import androidx.annotation.NonNull

import kotlinx.parcelize.Parcelize

@Parcelize
data class Message (

    var id: Int? = -1,
    @NonNull
    var sender: String = "",
    @NonNull
    var recipient: String = "",
    @NonNull
    var date: String = "",
    @NonNull
    var time: String = "",
    @NonNull
    var message: String = ""
): Parcelable