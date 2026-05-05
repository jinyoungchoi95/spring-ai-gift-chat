package gift.chat.external

import org.springaicommunity.claude.agent.sdk.Query
import org.springaicommunity.claude.agent.sdk.QueryOptions
import org.springframework.stereotype.Component

@Component
class ClaudeCodeGiftRecommendChatClient : GiftRecommendChatClient {

    override fun call(message: String, sessionId: String): Result<String> {
        return runCatching {
            val options = QueryOptions.builder()
                .systemPrompt(GIFT_RECOMMEND_SYSTEM)
                .build()

            Query.text(message, options).also {
                require(!it.isNullOrEmpty()) { "content is empty" }
            }
        }
    }

    companion object {
        private val GIFT_RECOMMEND_SYSTEM = """
            당신은 선물 추천 전문가입니다.
            사용자가 선물 받을 대상과 상황을 설명하면 적절한 상품을 추천하세요.
            ## 규칙
            - 한국어로 답변
            - 추천은 1개만 제안
            - 선물과 관련 없는 질문에는 정중히 거절하고, 선물 추천으로 대화를 유도
        """.trimIndent()
    }
}
