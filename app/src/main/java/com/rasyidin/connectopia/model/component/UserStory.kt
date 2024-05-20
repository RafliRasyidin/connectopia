package com.rasyidin.connectopia.model.component

data class UserStory(
    val id: String,
    val userId: String,
    val story: String,
    val createdAt: Long,
    val validUntil: Long,
    val isViewed: Boolean,
)

data class Stories(
    val userId: String,
    val stories: List<UserStory>,
    val username: String
) {
    fun isStoriesViewed(): Boolean {
        return stories.all { it.isViewed }
    }
}

val dummyStories = listOf(
    Stories(
        userId = "",
        username = "Rasyidin",
        stories = listOf(
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
        )
    ),
    Stories(
        userId = "",
        username = "Rasyidin",
        stories = listOf(
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
        )
    ),
    Stories(
        userId = "",
        username = "Rasyidin",
        stories = listOf(
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
        )
    ),
    Stories(
        userId = "",
        username = "Rasyidin",
        stories = listOf(
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
            UserStory(
                id = "123",
                userId = "",
                story = "https://picsum.photos/200/300",
                createdAt = System.currentTimeMillis(),
                isViewed = false,
                validUntil = 0
            ),
        )
    )
)
