package gift.chat.external

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.springaicommunity.claude.agent.sdk.Query
import org.springaicommunity.claude.agent.sdk.QueryOptions

class ClaudeCodeGiftRecommendChatClientTest : FunSpec({
    isolationMode = IsolationMode.InstancePerTest

    val claudeCodeGiftRecommendChatClient = ClaudeCodeGiftRecommendChatClient()

    beforeTest {
        mockkStatic(Query::class)
    }

    afterTest {
        unmockkStatic(Query::class)
    }

    context("call") {
        test("ai 요청의 응답을 내린다") {
            every { Query.text(any<String>(), any<QueryOptions>()) } returns "생일 추천 선물은 케이크"

            val actual = claudeCodeGiftRecommendChatClient.call(
                message = "친구 생일 선물 추천해줘",
                sessionId = "550e8400-e29b-41d4-a716-446655440000",
            )
            actual.getOrNull() shouldBe "생일 추천 선물은 케이크"
        }

        test("ai 요청의 응답이 빈 문자열인 경우 fail Result를 내린다") {
            every { Query.text(any<String>(), any<QueryOptions>()) } returns ""

            val actual = claudeCodeGiftRecommendChatClient.call(
                message = "친구 생일 선물 추천해줘",
                sessionId = "550e8400-e29b-41d4-a716-446655440000",
            )
            actual.isFailure shouldBe true
            actual.exceptionOrNull()!!.message shouldBe "content is empty"
        }

        test("ai 요청의 응답이 null인 경우 fail Result를 내린다") {
            every { Query.text(any<String>(), any<QueryOptions>()) } returns null

            val actual = claudeCodeGiftRecommendChatClient.call(
                message = "친구 생일 선물 추천해줘",
                sessionId = "550e8400-e29b-41d4-a716-446655440000",
            )
            actual.isFailure shouldBe true
            actual.exceptionOrNull()!!.message shouldBe "content is empty"
        }

        test("ai 요청 응답이 에러가 나는 경우 fail Result를 내린다") {
            every { Query.text(any<String>(), any<QueryOptions>()) } throws RuntimeException("chat 에러")

            val actual = claudeCodeGiftRecommendChatClient.call(
                message = "친구 생일 선물 추천해줘",
                sessionId = "550e8400-e29b-41d4-a716-446655440000",
            )
            actual.isFailure shouldBe true
            actual.exceptionOrNull()!!.message shouldBe "chat 에러"
        }
    }
})
