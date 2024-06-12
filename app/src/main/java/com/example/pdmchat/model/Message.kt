package com.example.pdmchat.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.PrimaryKey

import kotlinx.parcelize.Parcelize

@Parcelize
data class Message (
    @PrimaryKey(autoGenerate = true)
    var id: String? = "",
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