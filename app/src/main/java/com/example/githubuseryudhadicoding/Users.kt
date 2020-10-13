package com.example.githubuseryudhadicoding

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users (
    var usersName: String? = null,
    var username: String? = null,
    var avatar: String? = null,
    var userFollowers: Int = 0,
    var userFollowing: Int = 0
) : Parcelable

