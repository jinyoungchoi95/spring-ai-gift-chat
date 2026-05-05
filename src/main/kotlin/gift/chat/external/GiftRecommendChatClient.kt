package gift.chat.external

interface GiftRecommendChatClient {
    fun call(message: String, sessionId: String): Result<String>
}
