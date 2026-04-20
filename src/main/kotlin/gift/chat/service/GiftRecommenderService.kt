package gift.chat.service

import gift.chat.dto.RecommendGiftRequest
import gift.chat.dto.RecommendGiftResponse
import gift.chat.exception.GiftRecommendException
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GiftRecommenderService(
    chatClientBuilder: ChatClient.Builder,
) {
    private val chatClient: ChatClient = chatClientBuilder
        .defaultSystem(GIFT_RECOMMEND_SYSTEM)
        .defaultAdvisors(LoggerAdvisor())
        .build()

    fun recommend(request: RecommendGiftRequest): RecommendGiftResponse {
        val sessionId = request.sessionId ?: UUID.randomUUID().toString()
        val response = chatClient.prompt()
            .user(request.message)
            .advisors { it.param("sessionId", sessionId) }
            .call()
            .content() ?: throw GiftRecommendException()

        return RecommendGiftResponse(
            sessionId = sessionId,
            message = response,
        )
    }

    companion object {
        private const val GIFT_RECOMMEND_SYSTEM = """
            당신은 선물 추천 전문가입니다.
            사용자가 선물 받을 대상과 상황을 설명하면 적절한 상품을 추천하세요.
            ## 규칙
            - 한국어로 답변
            - 추천은 1개만 제안
            - 선물과 관련 없는 질문에는 정중히 거절하고, 선물 추천으로 대화를 유도
        """
    }
}
