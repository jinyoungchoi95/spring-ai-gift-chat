package gift.chat.external

import gift.chat.service.LoggerAdvisor
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.ChatClient.Builder

class SpringAiGiftRecommendChatClient(
    chatClientBuilder: Builder,
): GiftRecommendChatClient {
    private val chatClient: ChatClient = chatClientBuilder
        .defaultSystem(GIFT_RECOMMEND_SYSTEM)
        .defaultAdvisors(LoggerAdvisor())
        .build()

    override fun call(message: String, sessionId: String): Result<String> {
        return runCatching {
            chatClient.prompt()
                .user(message)
                .advisors { it.param(ADVISOR_SESSION_ID_KEY, sessionId) }
                .call()
                .content() ?: error("content is empty")
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
        private const val ADVISOR_SESSION_ID_KEY = "sessionId"
    }
}
