package gift.chat.service

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.equalTo
import org.logcapture.assertion.ExpectedLoggingMessage.aLog
import org.logcapture.kotest.LogCaptureListener
import org.springframework.ai.chat.client.ChatClientRequest
import org.springframework.ai.chat.client.ChatClientResponse
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt

class LoggerAdvisorTest : FunSpec({
    isolationMode = IsolationMode.InstancePerTest

    val logCaptureListener = LogCaptureListener()
    extensions(logCaptureListener)

    val loggerAdvisor = LoggerAdvisor()

    context("getName") {
        test("본인의 클래스 명을 응답한다") {
            val actual = loggerAdvisor.name
            actual shouldBe "LoggerAdvisor"
        }
    }

    context("getOrder") {
        test("최우선순위 0로 응답한다") {
            val actual = loggerAdvisor.order
            actual shouldBe 0
        }
    }

    context("adviseCall") {
        test("요청 메시지와 응답/시간을 로깅한다") {
            val sessionId = "550e8400-e29b-41d4-a716-446655440000"
            val chatClientRequest = mockk<ChatClientRequest>(relaxed = true)
            val callAdvisorChain = mockk<CallAdvisorChain>()
            val chatClientResponse = mockk<ChatClientResponse>(relaxed = true)

            every { chatClientRequest.prompt().userMessage.text } returns "친구 생일 선물 추천해줘"
            every { chatClientRequest.context } returns mapOf("sessionId" to sessionId)
            every { callAdvisorChain.nextCall(chatClientRequest) } returns chatClientResponse

            loggerAdvisor.adviseCall(chatClientRequest, callAdvisorChain)

            logCaptureListener.logged(
                aLog().info().withMessage(
                    equalTo("userMessage=친구 생일 선물 추천해줘 context={sessionId=$sessionId}")
                )
            )
            logCaptureListener.logged(aLog().info().withMessage(containsString("durationMs=")))
        }
    }
})
