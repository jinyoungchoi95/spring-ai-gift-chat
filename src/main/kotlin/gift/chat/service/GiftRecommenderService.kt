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
            .system(GIFT_RECOMMEND_SYSTEM)
            .user(request.message)
            .call()
            .content()

        return RecommendGiftResponse(
            requestId = "550e8400-e29b-41d4-a716-446655440000",
            message = response!!,
            durationMs = 500,
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
