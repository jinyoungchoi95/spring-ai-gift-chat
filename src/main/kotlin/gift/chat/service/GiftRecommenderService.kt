package gift.chat.service

import gift.chat.controller.RecommendGiftRequest
import gift.chat.controller.RecommendGiftResponse
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class GiftRecommenderService(
    val chatClient: ChatClient,
) {
    fun recommend(request: RecommendGiftRequest): RecommendGiftResponse {
        val response = chatClient.prompt()
            .call()
            .content()

        return RecommendGiftResponse(
            requestId = "550e8400-e29b-41d4-a716-446655440000",
            message = response!!,
            durationMs = 500,
        )
    }
}
