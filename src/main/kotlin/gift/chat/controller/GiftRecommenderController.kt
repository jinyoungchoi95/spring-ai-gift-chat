package gift.chat.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GiftRecommenderController {
    @PostMapping("/api/gifts/recommend")
    fun recommend(@RequestBody request: RecommendGiftRequest): RecommendGiftResponse {
        return RecommendGiftResponse(
            requestId = "550e8400-e29b-41d4-a716-446655440000",
            message = "생일 추천 선물은 케이크",
            durationMs = 500,
        )
    }
}
