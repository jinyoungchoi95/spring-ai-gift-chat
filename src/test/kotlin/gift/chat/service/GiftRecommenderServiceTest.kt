package gift.chat.service

import gift.chat.controller.RecommendGiftRequest
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.ai.chat.client.ChatClient

class GiftRecommenderServiceTest : FunSpec({
    isolationMode = IsolationMode.InstancePerTest

    val chatClient = mockk<ChatClient>()
    val chatClientBuilder = mockk<ChatClient.Builder>()

    beforeTest {
        every { chatClientBuilder.defaultSystem(any<String>()) } returns chatClientBuilder
        every { chatClientBuilder.build() } returns chatClient
    }

    val giftRecommenderService by lazy {
        GiftRecommenderService(chatClientBuilder = chatClientBuilder)
    }

    context("recommend") {
        test("선물 추천을 ai client를 통해 요청한다") {
            val request = RecommendGiftRequest(
                message = "친구 생일 선물 추천해줘",
                sessionId = null
            )
            every {
                chatClient.prompt()
                    .user("친구 생일 선물 추천해줘")
                    .call()
                    .content()
            } returns "생일 추천 선물은 케이크"

            val actual = giftRecommenderService.recommend(request)
            actual.message shouldBe "생일 추천 선물은 케이크"
        }
    }
})
