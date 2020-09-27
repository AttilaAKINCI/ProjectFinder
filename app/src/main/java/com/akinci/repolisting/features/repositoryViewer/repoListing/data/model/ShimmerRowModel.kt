package com.akinci.repolisting.features.repositoryViewer.repoListing.data.model

import com.akinci.repolisting.commons.data.model.ListRowDataContract

data class ShimmerRowModel (
    var isStarEnabled : Boolean = (0 until 2).random() == 1
) : ListRowDataContract