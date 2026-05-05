package gift.chat.config

import gift.chat.external.ClaudeCodeGiftRecommendChatClient
import gift.chat.external.GiftRecommendChatClient
import gift.chat.external.SpringAiGiftRecommendChatClient
import org.springframework.ai.chat.client.ChatClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GiftRecommendChatClientConfig {

    @Bean
    @ConditionalOnProperty(name = ["llm.provider"], havingValue = "spring-ai")
    fun springAiGiftRecommendChatClient(chatClientBuilder: ChatClient.Builder): GiftRecommendChatClient {
        return SpringAiGiftRecommendChatClient(chatClientBuilder)
    }

    @Bean
    @ConditionalOnProperty(name = ["llm.provider"], havingValue = "claude-code")
    fun claudeCodeGiftRecommendChatClient(): GiftRecommendChatClient {
        return ClaudeCodeGiftRecommendChatClient()
    }
}
