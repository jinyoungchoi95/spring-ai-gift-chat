package gift.chat.service

import gift.chat.controller.RecommendGiftRequest
import gift.chat.controller.RecommendGiftResponse
import org.springframework.stereotype.Service

@Service
class GiftRecommenderService(

) {
    fun recommend(request: RecommendGiftRequest): RecommendGiftResponse {
        return RecommendGiftResponse(
            requestId = "550e8400-e29b-41d4-a716-446655440000",
            message = "생일 추천 선물은 케이크",
            durationMs = 500,
        )
    }
}
