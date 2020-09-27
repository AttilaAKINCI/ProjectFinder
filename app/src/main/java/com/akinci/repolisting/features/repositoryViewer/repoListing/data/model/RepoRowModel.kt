package com.akinci.repolisting.features.repositoryViewer.repoListing.data.model

import android.os.Parcelable
import com.akinci.repolisting.commons.data.model.ListRowDataContract
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.RepoFavoriteInfoEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoRowModel (
    var id : Long,
    var name : String,
    var url : String,
    var owner : OwnerModel,
    var stargazers_count : Int,
    var open_issues_count : Int,
    var description : String,
    var language : String,
    var isFav : Boolean = false
) : Parcelable, ListRowDataContract

/** For integrity between network and ROOM Database **/
fun RepoRowModel.asDataBaseModel() : RepoFavoriteInfoEntity {
    var repoFavoriteInfo : RepoFavoriteInfoEntity
    apply {
        repoFavoriteInfo = RepoFavoriteInfoEntity(
            this.id,
            this.name,
            this.owner.login
        )
    }
    return repoFavoriteInfo
}