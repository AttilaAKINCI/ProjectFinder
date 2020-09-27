package com.akinci.repolisting.features.repositoryViewer.repoListing.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OwnerModel (
    var id : Int = 0,
    var login : String = "",
    var avatar_url : String = ""
) : Parcelable {
    constructor(repoOwnerName:String) : this(
        login = repoOwnerName
    )
}