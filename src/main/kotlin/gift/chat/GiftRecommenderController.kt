package gift.chat

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GiftRecommenderController {
    @PostMapping("/api/gifts/recommend")
    fun recommend(): String {
        return "생일 추천 선물은 케이크"
    }
}
