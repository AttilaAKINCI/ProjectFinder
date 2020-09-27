package com.akinci.repolisting.features.repositoryViewer.repoListing.data.local

class DBConstants {
    companion object{
        /** DB Related **/
        @JvmStatic val DB_NAME = "repoDB"

        /** Entity Related **/
        const val REPOS_TABLE_NAME = "repotable"

        const val RECORD_ID = "recordId"
        const val REPO_ID = "repoId"
        const val REPO_NAME = "repoName"
        const val REPO_OWNER_NAME = "repoOwnerName"
    }
}