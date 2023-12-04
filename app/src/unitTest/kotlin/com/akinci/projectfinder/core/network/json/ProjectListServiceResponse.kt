package com.akinci.projectfinder.core.network.json

object ProjectListServiceResponse {
    val fail = """
        {
            "message": "Not Found",
            "documentation_url": "https://docs.github.com/rest/repos/repos#list-repositories-for-a-user"
        }
        """
    val success = """
            [
                {
                    "id": 353730900,
                    "name": "Chatter",
                    "url": "https://api.github.com/repos/AttilaAKINCI/Chatter",
                    "owner": {
                        "id": 21987335,
                        "login": "AttilaAKINCI",
                        "avatar_url": "https://avatars.githubusercontent.com/u/21987335?v=4"
                    },
                    "stargazers_count": 10,
                    "open_issues_count": 0,
                    "language": "Kotlin",
                    "description": "Chatting Sample App"
                },
                {
                    "id": 440251794,
                    "name": "DoggoApp",
                    "url": "https://api.github.com/repos/AttilaAKINCI/DoggoApp",
                    "owner": {
                        "id": 21987335,
                        "login": "AttilaAKINCI",
                        "avatar_url": "https://avatars.githubusercontent.com/u/21987335?v=4"
                    },
                    "stargazers_count": 2,
                    "open_issues_count": 0,
                    "language": "Kotlin",
                    "description": "Doggo App provides a inspection on dogs according to their breeds and sub-breeds"
                }
            ]
        """.trimIndent()
}