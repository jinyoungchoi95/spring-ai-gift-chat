package gift.chat.service

import gift.chat.dto.RecommendGiftRequest
import gift.chat.dto.RecommendGiftResponse
import gift.chat.exception.GiftRecommendException
import gift.chat.external.GiftRecommendChatClient
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GiftRecommenderService(
    private val chatClient: GiftRecommendChatClient,
) {
    fun recommend(request: RecommendGiftRequest): RecommendGiftResponse {
        val sessionId = request.sessionId ?: UUID.randomUUID().toString()
        val response = chatClient.call(request.message, sessionId)
            .onFailure { log.error(it) { "ai prompt call error" } }
            .getOrNull() ?: throw GiftRecommendException()

        return RecommendGiftResponse(
            sessionId = sessionId,
            message = response,
        )
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
