package gift.chat.controller

data class RecommendGiftRequest(
    val message: String,
    val sessionId: String?
)
