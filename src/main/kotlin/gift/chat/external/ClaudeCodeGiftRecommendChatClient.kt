package gift.chat.external

import org.springaicommunity.claude.agent.sdk.Query
import org.springaicommunity.claude.agent.sdk.QueryOptions

class ClaudeCodeGiftRecommendChatClient(
    private val systemPrompt: String,
) : GiftRecommendChatClient {

    override fun call(message: String, sessionId: String): Result<String> {
        return runCatching {
            val options = QueryOptions.builder()
                .systemPrompt(systemPrompt)
                .build()

            Query.text(message, options).also {
                require(!it.isNullOrEmpty()) { "content is empty" }
            }
        }
    }
}
