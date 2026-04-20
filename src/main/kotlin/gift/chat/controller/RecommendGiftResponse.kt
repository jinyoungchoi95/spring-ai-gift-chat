package gift.chat.controller

data class RecommendGiftResponse(
    val requestId: String,
    val message: String,
    val durationMs: Long,
)
