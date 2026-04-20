package gift.chat.controller

import gift.chat.dto.RecommendGiftRequest
import gift.chat.dto.RecommendGiftResponse
import gift.chat.service.GiftRecommenderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GiftRecommenderController(
    private val giftRecommenderService: GiftRecommenderService,
) {
    @PostMapping("/api/gifts/recommend")
    fun recommend(@RequestBody request: RecommendGiftRequest): RecommendGiftResponse {
        return giftRecommenderService.recommend(request)
    }
}
